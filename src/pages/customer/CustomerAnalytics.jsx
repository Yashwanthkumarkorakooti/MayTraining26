import { useEffect, useState } from "react";
import axios from "axios";
import { Chart } from "primereact/chart";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";
import Loader from "../../components/common/Loader";

const CustomerAnalytics = () => {

  const customerId =localStorage.getItem("userId");

  const [loading,setLoading] = useState(true);
  const [monthlyChart,setMonthlyChart] = useState({});
  const [accountChart,setAccountChart] = useState({});
  const [transactionChart,setTransactionChart] = useState({});

  useEffect(() => {
    fetchAnalytics();

  }, []);

  const fetchAnalytics = async () => {
      try {
        const config = {
          headers: {
            Authorization:"Bearer " + localStorage.getItem("token"),
          }
        }

        const [spendingResp,accountResp,transactionResp] = await Promise.all([
          axios.get(
            'http://localhost:8080/api/v1/customers/analytics/monthly-spending',
            config
          ),
          axios.get(
            'http://localhost:8080/api/v1/customers/analytics/account-distribution',
            config
          ),
          axios.get(
            'http://localhost:8080/api/v1/customers/analytics/transaction-types',
            config
          ),
        ]);

        // BAR CHART
        setMonthlyChart({
          labels:spendingResp.data.map((item) =>item.month),

          datasets: [
            {
              label:
                "Monthly Spending",
              data:
                spendingResp.data.map((item) =>item.amount)
            }
          ]
        })

        // PIE CHART
        setAccountChart({
          labels:accountResp.data.map((item) =>item.accountType),

          datasets: [
            {
              data:
                accountResp.data.map((item) =>item.count
                )
            }
          ]
        })

        // PIE CHART
        setTransactionChart({
          labels:
            transactionResp.data.map((item) =>item.transactionType),

          datasets: [
            {
              data:
                transactionResp.data.map(
                  (item) =>
                    item.count
                )
            }
          ]
        })

      } catch (error) {
        console.log(error);
      } finally {
        setLoading(false);
      }
    };

  if (loading) {
    return <Loader />;
  }

  return (
  <div>

    <div className="page-content">

      <div className="container-fluid">

        <div className="row justify-content-center">

          {/* Monthly Spending */}
          <div className="col-12 col-md-12 col-lg-10 mb-4">

            <div className="card chart-card shadow border-0 h-100">

              <div className="card-body text-center">

                <h4 className="mb-4">
                  Monthly Spending
                </h4>

                <Chart
                  type="bar"
                  data={monthlyChart}
                  options={{
                    responsive: true,
                    maintainAspectRatio: false,
                  }}
                  style={{
                    height: "400px"
                  }}
                />

              </div>

            </div>

          </div>

          {/* Account Distribution */}
          <div className="col-12 col-md-6 mb-4">

            <div className="card chart-card shadow border-0 h-100">

              <div className="card-body text-center">

                <h4 className="mb-4">
                  Account Distribution
                </h4>

                <Chart
                  type="pie"
                  data={accountChart}
                  options={{
                    responsive: true,
                    maintainAspectRatio: true,
                  }}
                />

              </div>

            </div>

          </div>

          {/* Transaction Types */}
          <div className="col-12 col-md-6 mb-4">

            <div className="card chart-card shadow border-0 h-100">

              <div className="card-body text-center">

                <h4 className="mb-4">
                  Transaction Types
                </h4>

                <Chart
                  type="pie"
                  data={transactionChart}
                  options={{
                    responsive: true,
                    maintainAspectRatio: true,
                  }}
                />

              </div>

            </div>

          </div>

        </div>

      </div>

    </div>

  </div>
)
}

export default CustomerAnalytics;
import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";
import Loader from "../../components/common/Loader";

const LoanEligibility = () => {

  const customerId = localStorage.getItem("userId");
  const [eligibility,setEligibility] = useState(null);
  const [loading,setLoading] = useState(true);
  const loanEligibilityApi = 'http://localhost:8080/api/v1/customers/loan-eligibility';

  useEffect(() => {
    fetchEligibility();
  }, []);

  const fetchEligibility = async () => {
      try {
        const config = {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
          }
        }

        const response = await axios.get(loanEligibilityApi,config)
        setEligibility(response.data)
      } catch (error) {
        console.log(error);
      } finally {
        setLoading(false);
      }
    }

  if (loading) {
    return <Loader />;
  }

  return (
    <div>

      <Navbar />
      <CustomerSidebar />

      <div className="page-content">
        <div className="card shadow border-0">
          <div className="card-body p-4">
            <h2 className="mb-4">
              Loan Eligibility
            </h2>
            <div className="row">
              <div className="col-md-6 mb-3">
                <strong> Average Monthly Balance </strong>
                <p> ₹ {eligibility.averageMonthlyBalance } </p>
              </div>

              <div className="col-md-6 mb-3">
                <strong> Inbound Cash Flow </strong>
                <p> ₹ {eligibility.inboundCashFlow}</p>
              </div>
              <div className="col-md-6 mb-3">
                <strong> Outbound Cash Flow </strong>
                <p> ₹{eligibility.outboundCashFlow} </p>
              </div>

              <div className="col-md-6 mb-3">
                <strong> EMI Burden</strong>
                <p> ₹{eligibility.emiBurden} </p>
              </div>

              {/* <div className="col-md-6 mb-3">
                <strong>Eligible Amount </strong>
                <p className="text-success fw-bold fs-4"> ₹{eligibility.eligibleAmount} </p>
              </div> */}

              <div className="col-md-6 mb-3">
                <strong> Recommended Tenure </strong>
                <p> { eligibility.recommendedTenure} Months</p>
              </div>

              <div className="col-md-12">
                <strong> Risk Score </strong>
                <p className="badge bg-primary fs-6">
                  { eligibility.riskScore }
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default LoanEligibility;
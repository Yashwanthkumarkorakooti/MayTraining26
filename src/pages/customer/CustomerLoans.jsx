import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";
import Loader from "../../components/common/Loader";

const CustomerLoans = () => {

  const customerId = localStorage.getItem("userId");
  const navigate = useNavigate();
  const [loans, setLoans] = useState([]);
  const [loading,setLoading] = useState(true);

  const loansApi = "http://localhost:8080/api/v1/customers/loans";

  useEffect(() => {
    fetchLoans();
  }, []);

  const fetchLoans = async () => {
      try {
        const config = {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
          }
        }

        const response = await axios.get(loansApi,config)
        setLoans(response.data)
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
        <div className="card table-card shadow border-0">
          <div className="card-body">
            <div className="d-flex justify-content-between align-items-center mb-4">
              <h2> My Loans </h2>

              <button className="btn btn-success"
                onClick={() =>navigate("/customer/apply-loan")}>
                Apply Loan
              </button>

            </div>

            <div className="table-responsive">
              <table className="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>EMI</th>
                    <th>Status</th>
                    <th>Action</th>
                  </tr>
                </thead>

                <tbody>
                  {
                    loans.length > 0 ?

                      loans.map((loan) => (
                          <tr key={loan.loanId}>
                            <td> {loan.loanId} </td>
                            <td> {loan.loanType} </td>
                            <td> ₹ {loan.loanAmount} </td>
                            <td>₹ {loan.emiAmount} </td>
                            <td> { loan.loanStatus} </td>
                            <td>
                              <button className="btn btn-primary btn-sm me-2"
                                onClick={() => navigate( `/customer/loan-details/${loan.loanId}`)}>
                                View
                              </button>
                              {
                                loan.loanStatus === "DISBURSED" && (
                                  <button className="btn btn-warning btn-sm"
                                    onClick={() => navigate( `/customer/pay-emi/${loan.loanId}`)}>
                                    Pay EMI
                                  </button>
                                )
                              }
                            </td>
                          </tr>
                        )) :
                      (
                        <tr>
                          <td colSpan="6" className="text-center" > No Loans Found </td>
                        </tr>
                      )
                  }

                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default CustomerLoans;
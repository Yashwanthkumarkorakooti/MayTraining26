import { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";
import Loader from "../../components/common/Loader";

const LoanDetails = () => {

  const { loanId } = useParams();
  const [loan, setLoan] = useState(null);
  const [loading, setLoading] = useState(true);

  const loanDetailsApi = `http://localhost:8080/api/v1/loans/${loanId}`;

  useEffect(() => {
    fetchLoanDetails()
  }, [])

  const fetchLoanDetails = async () => {
      try {
        const config = {
          headers: {
            Authorization:"Bearer " +localStorage.getItem("token")
          }
        }

        const response = await axios.get(loanDetailsApi,config)
        setLoan(response.data)
        console.log(response.data)
      } catch (error) {
        console.log(error);
      } finally {
        setLoading(false);
      }
    }

  if (loading) {
    return <Loader />
  }

  return (
    <div>

      <Navbar />
      <CustomerSidebar />

      <div className="page-content">
        <div className="card shadow border-0">
          <div className="card-body">
            <h2 className="mb-4">
              Loan Details
            </h2>

            <p><strong>Customer: </strong> {loan.customerName} </p>
            <p>  <strong> Loan Type: </strong>{loan.loanType}</p>
            <p> <strong> Amount:</strong> ₹{loan.loanAmount} </p>
            <p> <strong> Remaining: </strong>₹{loan.remainingBalance} </p>
            <p> <strong> EMI: </strong> ₹ {loan.emiAmount}</p>
            <p> <strong>  Status:</strong> {loan.loanStatus} </p>

          </div>
        </div>
      </div>
    </div>
  )
}

export default LoanDetails;
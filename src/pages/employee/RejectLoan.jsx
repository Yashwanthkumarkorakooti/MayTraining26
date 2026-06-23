import { useState } from "react";
import axios from "axios";
import {
  useNavigate,
  useParams,
} from "react-router-dom";

import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";

const RejectLoan = () => {

  const { loanId } = useParams();
  const navigate = useNavigate();
  const [reason,setReason] = useState("");
  const [successMsg,setSuccessMsg] = useState("");
  const [errMsg,setErrMsg] = useState("");

  const handleReject = async (e) => {
      e.preventDefault();
      try {

        const config = {
          headers: {
            Authorization:"Bearer " +localStorage.getItem("token")
          }
        }

        const payload = {
          reason,
        }

        await axios.put(`http://localhost:8080/api/v1/employees/loans/reject/${loanId}`,payload,config)
        setSuccessMsg("Loan Rejected Successfully")

        setTimeout(() => {
          navigate("/employee/loan-review")
        }, 1500)

      } catch (error) {
        setErrMsg(error.response?.message ||
                    error.response?.data?.message ||
                    error.response?.data ||
                    "Fail to reject Loan"
                )
            console.log(error.response?.data?.message)
      }
    }

  return (
    <div>

      <Navbar />
      <EmployeeSidebar />

      <div className="page-content">
        <div className="card shadow border-0 form-card">
          <div className="card-body">

            <h2 className="mb-4">
              Reject Loan
            </h2>

            {
              successMsg && (
                <div className="alert alert-success">
                  {successMsg}
                </div>
              )
            }

            {
              errMsg && (
                <div className="alert alert-danger">
                  {errMsg}
                </div>
              )
            }

            <form onSubmit={handleReject}>

              <div className="mb-4">
                <label className="form-label">
                  Rejection Reason
                </label>

                <textarea
                  className="form-control"
                  rows="4"
                  placeholder="Enter rejection reason"
                  value={reason}
                  onChange={(e) =>setReason(e.target.value)}
                  required
                ></textarea>

              </div>

              <button className="btn btn-danger submit-btn">
                Reject Loan
              </button>

            </form>
          </div>
        </div>
      </div>
    </div>
  )
}

export default RejectLoan;
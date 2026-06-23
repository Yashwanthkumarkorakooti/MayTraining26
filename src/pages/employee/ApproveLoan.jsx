import { useEffect, useState } from "react";
import axios from "axios";
import {
    useNavigate,
    useParams,
} from "react-router-dom";

import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";
import Loader from "../../components/common/Loader";

const ApproveLoan = () => {

    const { loanId } = useParams();
    const navigate = useNavigate();
    const [review,setReview] = useState(null);
    const [errMsg, setErrMsg] = useState('')
    const [loading,setLoading] = useState(true);
    const [successMsg,setSuccessMsg] = useState("");

    const reviewApi = `http://localhost:8080/api/v1/employees/loans/review/${loanId}`;

    useEffect(() => {
        fetchReview();
    }, []);

    const fetchReview = async () => {
            try {
                const config = {
                    headers: {
                        Authorization:
                            "Bearer " + localStorage.getItem("token")
                    }
                }

                const response = await axios.get(reviewApi,config)

                setReview(response.data)
                setSuccessMsg("")
                setErrMsg('')
                console.log(response.data)

            } catch (error) {

                console.log(error)
                console.log(error.response?.data)
                setSuccessMsg("")

                setErrMsg(
                    error.response?.data?.message
                    || "Loan already processed"
                )
            } finally {
                setLoading(false);
            }
        }

    const approveLoan = async () => {
            try {
                const config = {
                    headers: {
                        Authorization:"Bearer " + localStorage.getItem("token")
                    }
                }

                const response = await axios.put(`http://localhost:8080/api/v1/employees/loans/approve/${loanId}`,{},config)
                console.log(response)

                setErrMsg("");
                setSuccessMsg("Loan Approved Successfully")

                setTimeout(() => {
                    navigate("/employee/loan-review")
                }, 1500);

            } catch (error) {
                console.log(error);
                setErrMsg(
                    error.response?.data?.message ||
                    "Loan already processed"
                )
            }
        }

    if (loading) {
        return <Loader />
    }

    return (
        <div>

            <Navbar />
            <EmployeeSidebar />

            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body p-4">
                        <h2 className="mb-4">
                            Loan Review
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
                        <div className="row">
                            <div className="col-md-6 mb-3">
                                <strong>
                                    Customer Name
                                </strong>
                                <p>
                                    {
                                        review.customerName
                                    }
                                </p>
                            </div>

                            <div className="col-md-6 mb-3">
                                <strong>
                                    Account Number
                                </strong>
                                <p>
                                    {
                                        review.accountNumber
                                    }
                                </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <strong>
                                    Laon Amount
                                </strong>
                                <p>
                                    ₹
                                    {
                                        review.loanAmount
                                    }
                                </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <strong>
                                    Average Deposit
                                </strong>
                                <p>
                                    ₹
                                    {
                                        review.avgDeposit
                                    }
                                </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <strong>
                                    Average Withdrawal
                                </strong>
                                <p>
                                    ₹
                                    {
                                        review.avgWithdrawal
                                    }
                                </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <strong>
                                    Existing Loans
                                </strong>
                                <p>
                                    {
                                        review.existingLoans
                                    }
                                </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <strong>
                                    Risk 
                                </strong>
                                <p className="badge bg-danger">
                                    {
                                        review.risk
                                    }
                                </p>
                            </div>
                        </div>
                        <button
                            className="btn btn-success me-2"
                            onClick={approveLoan}>
                            Approve Loan
                        </button>

                        <button
                            className="btn btn-danger"
                            onClick={() =>
                                navigate(`/employee/reject-loan/${loanId}`)} >
                            Reject Loan
                        </button>

                    </div>
                </div>
            </div>
        </div>
    )
}

export default ApproveLoan;
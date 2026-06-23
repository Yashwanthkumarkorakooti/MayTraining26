import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";

const DisburseLoan = () => {

    const [loans, setLoans] = useState([]);
    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const config = {
        headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    }

    useEffect(() => {
        fetchLoans();
    }, []);

    const fetchLoans = async () => {
        try {
            const response =await axios.get("http://localhost:8080/api/v1/employees/assigned-loans",config)
            setLoans(response.data);
            console.log(response.data)
        } catch (error) {
            console.log(error);
        }
    };

    const disburseLoan = async (loanId) => {
        try {
            const response =
                await axios.post(`http://localhost:8080/api/v1/employees/loans/${loanId}/disburse`,{},config)

            setSuccessMsg(response.data.message)
            setErrMsg("")
            fetchLoans()
        } catch (error) {
            setSuccessMsg("")
            setErrMsg(
                error.response?.data?.message ||
                "Failed To Disburse Loan"
            )
        }
    }

    return (
        <div>

            <Navbar />
            <EmployeeSidebar />

            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body">
                        <h2 className="mb-4">
                            Disburse Loans
                        </h2>
                        {
                            successMsg &&
                            <div className="alert alert-success">
                                {successMsg}
                            </div>
                        }

                        {
                            errMsg &&
                            <div className="alert alert-danger">
                                {errMsg}
                            </div>
                        }

                        <div className="table-responsive">
                            <table className="table table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Customer</th>
                                    <th>Loan Type</th>
                                    <th>Amount</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                {
                                    loans.map(loan => (
                                        <tr key={loan.loanId}>
                                            <td>
                                                {loan.loanId}
                                            </td>
                                            <td>
                                                {loan.customerName}
                                            </td>
                                            <td>
                                                {loan.loanType}
                                            </td>
                                            <td>
                                                ₹{loan.loanAmount}
                                            </td>
                                            <td>
                                                <span className="badge bg-success">
                                                    {loan.loanStatus}
                                                </span>
                                            </td>
                                            <td>
                                                <button
                                                    className="btn btn-primary btn-sm"
                                                    onClick={() => disburseLoan(loan.loanId)}>
                                                    Disburse
                                                </button>
                                            </td>
                                        </tr>
                                    ))
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

export default DisburseLoan;
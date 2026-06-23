import { useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";
import { useNavigate } from "react-router-dom";

const ApplyLoan = () => {

    const navigate = useNavigate()

    const [formData,
        setFormData] =
        useState({
            loanType: "",
            loanAmount: "",
            loanTermMonths: "",
            purpose: "",
        });

    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg,setErrMsg] = useState("");

    const applyLoanApi = 'http://localhost:8080/api/v1/customers/loans/apply';

    const handleChange = (e) => {
            setFormData({
                ...formData,
                [e.target.name]:
                    e.target.value,
            });
        };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                    }
                }

            await axios.post(applyLoanApi,formData,config)
            setSuccessMsg("Loan Applied Successfully")
            } catch (error) {
                console.log(error.response?.data)
                console.log(error);
                setErrMsg(
                    error.response?.data?.message ||
                    "Loan Application Failed"
                )
            }
        }

    return (
        <div>

            <Navbar />
            <CustomerSidebar />

            <div className="page-content">
                <div className="card shadow border-0 form-card">
                    <div className="card-body">
                        <div className="d-flex justify-content-between align-items-center mb-4">
                            <h2>
                                Apply Loan
                            </h2>

                            <button
                                className="btn btn-primary"
                                onClick={() =>navigate("/customer/loan-eligibility")}>
                                Check Eligibility
                            </button>
                        </div>
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

                        <form onSubmit={handleSubmit}>
                            <select name="loanType" className="form-select mb-3"
                                onChange={handleChange} required>
                                <option value=""> Select Loan Type </option>
                                <option value="HOME"> HOME LOAN </option>
                                <option value="CAR"> CAR LOAN </option>
                                <option value="PERSONAL"> PERSONAL LOAN </option>
                                <option value="BUSINESS"> BUSINESS LOAN </option>
                                <option value="EDUCATION"> EDUCATION LOAN</option>
                            </select>

                            <input
                                type="number"
                                name="loanAmount"
                                className="form-control mb-3"
                                placeholder="Loan Amount"
                                onChange={handleChange}
                                required
                            />

                            <input
                                type="number"
                                name="loanTermMonths"
                                className="form-control mb-3"
                                placeholder="Loan Term Months"
                                onChange={handleChange}
                                required
                            />

                            <textarea
                                name="purpose"
                                className="form-control mb-3"
                                placeholder="Purpose"
                                onChange={handleChange}
                            ></textarea>

                            <button className="btn btn-primary submit-btn">
                                Apply Loan
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ApplyLoan;
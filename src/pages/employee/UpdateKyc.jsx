import { useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";

import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";

const UpdateKyc = () => {

    const { customerId } = useParams();

    const navigate = useNavigate();
    const [responseData, setResponseData] = useState(null);
    const [kycStatus, setKycStatus] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const updateKycApi = `http://localhost:8080/api/v1/employee/kyc-update/${customerId}`;

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const config = {
                headers: {
                    Authorization:"Bearer " + localStorage.getItem("token")
                }
            }

            const payload = {
                kycStatus,
            }

            const response = await axios.put(updateKycApi, payload, config)
            setResponseData(response.data);
            console.log(response.data)
            setSuccessMsg("KYC Updated Successfully");
            setErrMsg("");

        } catch (error) {
            console.log(error);
            setErrMsg("Failed To Update KYC")
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
                            Update KYC
                        </h2>

                        {/* {
                            errMsg && (
                                <div className="alert alert-danger">
                                    {errMsg}
                                </div>
                            )
                        } */}

                        <form onSubmit={handleSubmit}>
                            <div className="mb-4">
                                <label className="form-label">
                                    KYC Status
                                </label>

                                <select className="form-select" value={kycStatus}
                                    onChange={(e) => setKycStatus(e.target.value)}
                                    required>

                                    <option value=""> Select Status</option>
                                    <option value="PENDING"> PENDING </option>
                                    <option value="VERIFIED"> VERIFIED </option>
                                    <option value="REJECTED"> REJECTED </option>

                                </select>

                            </div>
                            <button className="btn btn-warning submit-btn">
                                Update KYC
                            </button>
                        </form>
                        {
                            responseData && (
                                <div className="card mt-4 border-success shadow-sm">
                                    <div className="card-body">

                                        <h4 className="text-success mb-3">
                                            <i className="bi bi-check-circle-fill me-2"></i>
                                            KYC Updated Successfully
                                        </h4>

                                        <p>
                                            <strong>Customer ID:</strong>
                                            {responseData.customerId}
                                        </p>

                                        <p>
                                            <strong>Customer Name:</strong>
                                            {responseData.customerName}
                                        </p>

                                        <p>
                                            <strong>Aadhaar:</strong>
                                            {responseData.aadhaarNumber}
                                        </p>

                                        <p>
                                            <strong>PAN:</strong>
                                            {responseData.panNumber}
                                        </p>

                                        <p>
                                            <strong>Status:</strong>
                                            <span
                                                className={
                                                    responseData.kycStatus === "VERIFIED"
                                                        ? "badge bg-success" : responseData.kycStatus === "REJECTED"
                                                        ? "badge bg-danger" : "badge bg-warning text-dark"
                                                }>
                                                {responseData.kycStatus}
                                            </span>
                                        </p>

                                    </div>
                                </div>
                            )
                        }

                    </div>
                </div>
            </div>
        </div>
    )
}

export default UpdateKyc;
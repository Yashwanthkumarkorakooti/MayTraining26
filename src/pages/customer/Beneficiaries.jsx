import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";

const Beneficiaries = () => {

    const [beneficiaries,setBeneficiaries] = useState([]);
    const [formData,setFormData] =useState({
            beneficiaryName: "",
            bankName: "",
            ifscCode: "",
            accountNumber: "",
            nickname: "",
        });

    const [successMsg,setSuccessMsg] =useState("");
    const [errMsg,setErrMsg] = useState("");

    const beneficiariesApi = 'http://localhost:8080/api/v1/customer/beneficiaries';

    const addBeneficiaryApi = 'http://localhost:8080/api/v1/customers/beneficiaries';

    useEffect(() => {
        const fetchBeneficiaries = async () => {
            try {
                const config = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                }
                const response = await axios.get(beneficiariesApi,config);
                setBeneficiaries(response.data);
            } catch (error) {
                console.log(error.response?.data);
                setErrMsg(
                    error.response?.data?.message ||
                    "Failed to Add Beneficiary"
                );
            }
        }
        fetchBeneficiaries()

    }, [])



    const handleChange = (e) => {

            setFormData({
                ...formData,
                [e.target.name]:
                    e.target.value,
            });
        };

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            const config = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                }
                await axios.post(addBeneficiaryApi,formData,config)
                setSuccessMsg("Beneficiary Added Successfully")
                fetchBeneficiaries();
            } catch (error) {
                console.log(error);
                setErrMsg("Failed to Add Beneficiary")
            }
        }

    const blockBeneficiary = async (beneficiaryId) => {
        try {
            const config = {
                headers: {
                    Authorization: "Bearer " +localStorage.getItem("token")
                    }
                }
                await axios.put(`http://localhost:8080/api/v1/beneficiaries/${beneficiaryId}/block`,{},config)
                fetchBeneficiaries();
            } catch (error) {
                console.log(error);
            }
        };

    return (
        <div>

            <Navbar />
            <CustomerSidebar />

            <div className="page-content">
                <div className="card shadow border-0 mb-4">
                    <div className="card-body">
                        <h2 className="mb-4">
                            Add Beneficiary
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
                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <input
                                        type="text"
                                        name="beneficiaryName"
                                        placeholder="Beneficiary Name"
                                        className="form-control"
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-md-6 mb-3">
                                    <input
                                        type="text"
                                        name="bankName"
                                        placeholder="Bank Name"
                                        className="form-control"
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-md-6 mb-3">
                                    <input
                                        type="text"
                                        name="ifscCode"
                                        placeholder="IFSC Code"
                                        className="form-control"
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-md-6 mb-3">
                                    <input
                                        type="text"
                                        name="accountNumber"
                                        placeholder="Account Number"
                                        className="form-control"
                                        onChange={handleChange}
                                        required
                                    />
                                </div>
                            </div>
                            <button className="btn btn-primary">
                                Add Beneficiary
                            </button>
                        </form>
                    </div>
                </div>
                <div className="card table-card shadow border-0">
                    <div className="card-body">
                        <h3 className="mb-4">
                            Beneficiaries
                        </h3>
                        <div className="table-responsive">
                            <table className="table">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Bank</th>
                                        <th>Account</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        beneficiaries.map((beneficiary) => (
                                                <tr key={beneficiary.beneficiaryId}>
                                                    <td> {beneficiary.beneficiaryName} </td>
                                                    <td> {beneficiary.bankName}  </td>
                                                    <td>{beneficiary.accountNumber}</td>
                                                    <td> {beneficiary.status}</td>
                                                    <td><button className="btn btn-danger btn-sm"
                                                            onClick={() =>blockBeneficiary(beneficiary.beneficiaryId)} >
                                                            Block
                                                        </button>
                                                    </td>
                                                </tr>
                                            )
                                        )
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Beneficiaries;
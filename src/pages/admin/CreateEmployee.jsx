import { useEffect, useState } from "react";
import axios from "axios";

import AdminSidebar from "../../components/admin/adminSidebar";
import Navbar from "../../components/common/Navbar";

const CreateEmployee = () => {

    const [successMsg, setSuccessMsg] = useState("");
    const [errmsg, setErrMsg] = useState("");
    const [branches, setBranches] = useState([]);

    const createApi = "http://localhost:8080/api/v1/admin/employees";
    const branchApi = "http://localhost:8080/api/v1/branches";

    const [formData, setFormData] = useState({
        username: "",
        password: "",
        email: "",
        phone: "",
        fullName: "",
        branchId: "",
        designation: "",
        salary: ""
    });

    const config = {
        headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    };

    useEffect(() => {
        getBranches();
    }, []);

    const getBranches = async () => {
        try {
            const response = await axios.get(branchApi, config);

            console.log("Branches:", response.data);

            setBranches(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            ...formData,
            branchId: Number(formData.branchId),
            salary: Number(formData.salary)
        };

        try {
            await axios.post(createApi, payload, config);

            setFormData({
                username: "",
                password: "",
                email: "",
                phone: "",
                fullName: "",
                branchId: "",
                designation: "",
                salary: ""
            });

            setSuccessMsg("Employee Created Successfully");
            setErrMsg("");

        } catch (error) {

            console.log(error);

            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Failed To Create Employee"
            );

            setSuccessMsg("");
        }
    };

    return (
        <div>
            <Navbar />
            <AdminSidebar />

            <div className="page-content">
                <div className="card shadow border-0 form-card">
                    <div className="card-body">

                        <h2 className="mb-4">
                            Create Employee
                        </h2>

                        {
                            successMsg &&
                            <div className="alert alert-success">
                                {successMsg}
                            </div>
                        }

                        {
                            errmsg &&
                            <div className="alert alert-danger">
                                {errmsg}
                            </div>
                        }

                        <form onSubmit={handleSubmit}>

                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label>Username</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="username"
                                        value={formData.username}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>
                            </div>

                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label>Password</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        name="password"
                                        value={formData.password}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>
                            </div>

                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label>Email</label>
                                    <input
                                        type="email"
                                        className="form-control"
                                        name="email"
                                        value={formData.email}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>
                            </div>

                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label>Full Name</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="fullName"
                                        value={formData.fullName}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>
                            </div>

                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label>Phone</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="phone"
                                        value={formData.phone}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>
                            </div>

                            {/* Branch Dropdown */}

                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label>Branch</label>

                                    <select
                                        className="form-select"
                                        name="branchId"
                                        value={formData.branchId}
                                        onChange={handleChange}
                                        required
                                    >
                                        <option value="">
                                            Select Branch
                                        </option>

                                        {branches.map((branch) => (
                                            <option
                                                key={branch.id}
                                                value={branch.id}
                                            >
                                                {branch.branch_name} ({branch.ifscCode})
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            </div>

                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label>Designation</label>

                                    <select
                                        className="form-select"
                                        name="designation"
                                        value={formData.designation}
                                        onChange={handleChange}
                                        required
                                    >
                                        <option value="">
                                            Select
                                        </option>

                                        <option value="MANAGER">
                                            Manager
                                        </option>

                                        <option value="ASSISTANT_MANAGER">
                                            Assistant Manager
                                        </option>

                                        <option value="CASHIER">
                                            Cashier
                                        </option>

                                        <option value="LOAN_OFFICER">
                                            Loan Officer
                                        </option>

                                        <option value="ACCOUNTANT">
                                            Accountant
                                        </option>

                                        <option value="CUSTOMER_SUPPORT">
                                            Customer Support
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label>Salary</label>

                                    <input
                                        type="number"
                                        className="form-control"
                                        name="salary"
                                        value={formData.salary}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>
                            </div>

                            <button
                                type="submit"
                                className="btn btn-primary submit-btn"
                            >
                                Create Employee
                            </button>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default CreateEmployee;
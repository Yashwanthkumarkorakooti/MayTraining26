import { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";

import Navbar from "../../components/common/Navbar";
import AdminSidebar from "../../components/admin/adminSidebar";

const TransferEmployee = () => {

    const { employeeId } = useParams();

    const [employee, setEmployee] = useState(null);
    const [branches, setBranches] = useState([]);

    const [newBranchId, setNewBranchId] = useState("");

    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const config = {
        headers: {
            Authorization:"Bearer " +localStorage.getItem("token")
        }
    }

    useEffect(() => {
        fetchEmployee()
        fetchBranches()
    }, [])

    const fetchEmployee = async () => {
        try {
            const response =
                await axios.get(`http://localhost:8080/api/v1/admin/employees/${employeeId}`,config)

            setEmployee(response.data);
        } catch (error) {
            console.log(error);
        }
    }

    const fetchBranches = async () => {
        try {
            const response =
                await axios.get("http://localhost:8080/api/v1/branches",config)

            setBranches(response.data);
        } catch (error) {
            console.log(error);
        }
    }

    const handleTransfer = async (e) => {
        e.preventDefault()

        setSuccessMsg("")
        setErrMsg("")

        try {
            const payload = {
                newBranchId: Number(newBranchId)
            }

            const response =
                await axios.put(`http://localhost:8080/api/v1/admin/employees/transfer/${employeeId}`,payload,config)
            console.log(response.data);
            setSuccessMsg(
                response.data.message ||
                "Employee transferred successfully"
            )
            fetchEmployee();
        } catch (error) {
            console.log(error);
            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Employee transfer unsuccessful"
            )
        }
    }

    return (
        <div>

            <Navbar />
            <AdminSidebar />

            <div className="page-content">
                <div className="card shadow border-0 form-card">
                    <div className="card-body">
                        <h2 className="mb-4">
                            Transfer Employee
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
                        {
                            employee && (

                                <div className="alert alert-info">

                                    <h5 className="mb-3">
                                        Employee Details
                                    </h5>

                                    <p>
                                        <strong>Name:</strong>{" "}
                                        {employee.fullName}
                                    </p>

                                    <p>
                                        <strong>Employee Code:</strong>{" "}
                                        {employee.employeeCode}
                                    </p>

                                    <p>
                                        <strong>Current Branch:</strong>{" "}
                                        {employee.branchName}
                                    </p>

                                </div>

                            )
                        }

                        <div className="alert alert-warning">
                            Select a new branch to transfer this employee.
                        </div>

                        <form onSubmit={handleTransfer}>
                            <div className="mb-4">
                                <label className="form-label">
                                    Select New Branch
                                </label>

                                <select
                                    className="form-select"
                                    value={newBranchId}
                                    onChange={(e) =>setNewBranchId(e.target.value)}
                                    required
                                >

                                    <option value="">
                                        Select Branch
                                    </option>

                                    {
                                        branches.map(branch => (
                                            <option
                                                key={branch.id}
                                                value={branch.id}
                                            >
                                                {branch.branch_name}
                                            </option>

                                        ))
                                    }

                                </select>

                            </div>

                            <button
                                type="submit"
                                className="btn btn-warning"
                            >
                                Transfer Employee
                            </button>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    )
}

export default TransferEmployee;
import axios from "axios";
import { useEffect, useState } from "react";
import Navbar from "../../components/common/Navbar";
import AdminSidebar from "../../components/admin/adminSidebar";
import { data } from "react-router-dom";

const AssignAccount = () => {

    const [accounts, setAccounts] = useState([]);
    const [employees, setEmployees] = useState([]);

    const [accountId, setAccountId] = useState("");
    const [employeeId, setEmployeeId] = useState("");

    const [selectedAccount, setSelectedAccount] = useState(null);
    const [selectedEmployee, setSelectedEmployee] = useState(null);

    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const config = {
        headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    }

    useEffect(() => {
        fetchAccounts()
        fetchEmployees()
    }, [])

    const fetchAccounts = async () => {
        try {
            const response =
                await axios.get("http://localhost:8080/api/v1/accounts",config)

            const pendingAccounts =
                response.data.filter(account =>account.accountStatus === "PENDING")

            setAccounts(response.data);
            console.log(response.data)

        } catch (error) {
            console.log(error);
        }
    };

    const fetchEmployees = async () => {
        try {

            const response =
                await axios.get("http://localhost:8080/api/v1/employes",config)
            setEmployees(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error);
        }
    }

    const handleAccountChange = (e) => {
        const id = e.target.value;
        setAccountId(id);

        const account = accounts.find(a =>String(a.accountId || a.id)=== String(id))
        setSelectedAccount(account);
    }

    const handleEmployeeChange = (e) => {
        const id = e.target.value;
        setEmployeeId(id);

        const employee = employees.find(emp =>String(emp.id) === String(id))

        setSelectedEmployee(employee);
    }

    const assignAccount = async (e) => {
        e.preventDefault();

        setSuccessMsg("");
        setErrMsg("");

        try {
            const response =
                await axios.put(
                    `http://localhost:8080/api/v1/admin/accounts/${accountId}/assign/${employeeId}`,{},config)

            setSuccessMsg(response.data);

        } catch (error) {
            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Assignment Failed"
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
                            Assign Account To Employee
                        </h2>

                        {
                            successMsg &&
                            (
                                <div className="alert alert-success">
                                    {successMsg}
                                </div>
                            )
                        }

                        {
                            errMsg &&
                            (
                                <div className="alert alert-danger">
                                    {errMsg}
                                </div>
                            )
                        }

                        <form onSubmit={assignAccount}>
                            <div className="mb-4">
                                <label className="form-label">
                                    Account
                                </label>

                                <select
                                    className="form-select"
                                    value={accountId}
                                    onChange={handleAccountChange}
                                    required>

                                    <option value="">
                                        Select Account
                                    </option>

                                    {
                                        accounts.map((account,idx) => (

                                            <option key={idx+1}
                                                value={account.id}
                                            >
                                                {
                                                    account.accountNumber
                                                }
                                                {" - "}
                                                {
                                                    account.customer.full_name
                                                }
                                                {" - "}
                                                {
                                                    account.branch?.branch_name || "No Branch"
                                                }
                                            </option>

                                        ))
                                    }

                                </select>

                            </div>

                            <div className="mb-4">

                                <label className="form-label">
                                    Employee
                                </label>

                                <select
                                    className="form-select"
                                    value={employeeId}
                                    onChange={handleEmployeeChange}
                                    required>

                                    <option value="">
                                        Select Employee
                                    </option>

                                    {
                                        employees.map((employee,idx) => (
                                            <option
                                                key={idx + 1}
                                                value={employee.id}
                                            >
                                                {
                                                    employee.fullName
                                                }
                                                {" - "}
                                                {
                                                    employee.designation
                                                }
                                                 {" - "}
                                                {
                                                    employee.branch.branch_name
                                                }
                                            </option>
                                        ))
                                    }

                                </select>

                            </div>

                            {
                                selectedAccount && (
                                    <div className="card border-primary mb-4">
                                        <div className="card-body">
                                            <h5>
                                                Account Details
                                            </h5>
                                            <p>
                                                <strong>
                                                    Customer:
                                                </strong>
                                                {
                                                    selectedAccount.customer.full_name
                                                }
                                            </p>

                                            <p>
                                                <strong>
                                                    Account:
                                                </strong>
                                                {
                                                    selectedAccount.accountNumber
                                                }
                                            </p>

                                            <p>
                                                <strong>
                                                    Type:
                                                </strong>
                                                {
                                                    selectedAccount.type
                                                }
                                            </p>

                                            <p>
                                                <strong>
                                                    Branch:
                                                </strong>
                                                {selectedAccount.branch?.branch_name || "N/A"}
                                            </p>

                                            <p>
                                                <strong>
                                                    KYC:
                                                </strong>
                                                {
                                                    selectedAccount.customer.kyc_status
                                                }
                                            </p>
                                        </div>
                                    </div>

                                )
                            }

                            {
                                selectedEmployee && (

                                    <div className="card border-success mb-4">

                                        <div className="card-body">

                                            <h5>
                                                Employee Details
                                            </h5>

                                            <p>
                                                <strong>
                                                    Name:
                                                </strong>
                                                {
                                                    selectedEmployee.fullName
                                                }
                                            </p>

                                            <p>
                                                <strong>
                                                    Designation:
                                                </strong>
                                                {
                                                    selectedEmployee.designation
                                                }
                                            </p>

                                            <p>
                                                <strong>
                                                    Branch:
                                                </strong>
                                                {
                                                    selectedEmployee.branch?.branch_name
                                                }
                                            </p>
                                        </div>
                                    </div>
                                )
                            }

                            <button
                                type="submit"
                                className="btn btn-primary">
                                Assign Account
                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </div>
    )
}

export default AssignAccount;
import axios from "axios";
import { useEffect, useState } from "react";
import Navbar from "../../components/common/Navbar";
import AdminSidebar from "../../components/admin/adminSidebar";

const AssignLoan = () => {

    const [loanId, setLoanId] = useState("");
    const [employeeId, setEmployeeId] = useState("");

    const [loans, setLoans] = useState([]);
    const [employees, setEmployees] = useState([]);

    const [selectedLoan, setSelectedLoan] = useState(null);
    const [selectedEmployee, setSelectedEmployee] = useState(null);

    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const config = {
        headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    }

    useEffect(() => {
        fetchLoans()
        fetchEmployees()
    }, [])

    const fetchLoans = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/v1/admin/pending-loans",config)

            setLoans(response.data);
            console.log("Loans:", response.data);

        } catch (error) {
            console.log(error);
        }
    }

    const fetchEmployees = async () => {
        try {

            const response = await axios.get("http://localhost:8080/api/v1/employes",config)

            setEmployees(response.data);
            console.log("Employees:", response.data);

        } catch (error) {
            console.log(error);
        }
    }

    const handleLoanChange = (e) => {
        const id = e.target.value;
        setLoanId(id);

        const loan = loans.find((l) => String(l.loanId ?? l.id) === String(id))
        setSelectedLoan(loan);
    }

    const handleEmployeeChange = (e) => {
        const id = e.target.value;
        setEmployeeId(id);

        const employee = employees.find((emp) =>String(emp.id) === String(id))
        setSelectedEmployee(employee);
    }

    const assignLoan = async (e) => {
        e.preventDefault();

        setSuccessMsg("");
        setErrMsg("");

        console.log("Loan ID:", loanId);
        console.log("Employee ID:", employeeId);

        try {

            const response = await axios.put(
                `http://localhost:8080/api/v1/admin/loans/${loanId}/assign/${employeeId}`,{},config)

            setSuccessMsg(
                response.data ||
                "Loan Assigned Successfully"
            )

        } catch (error) {
            console.log(error);

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
                <div className="card shadow border-0">
                    <div className="card-body">
                        <h2 className="mb-4">
                            Assign Loan
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

                        <form onSubmit={assignLoan}>
                            <div className="mb-4">
                                <label className="form-label fw-bold">
                                    Select Loan
                                </label>
                                <select
                                    className="form-select"
                                    value={loanId}
                                    onChange={handleLoanChange}
                                    required>

                                    <option value="">
                                        Select Loan
                                    </option>

                                    {
                                        loans.map((loan, idx) => (

                                            <option
                                                key={idx}
                                                value={loan.loanId}>
                                                {
                                                    loan.loanId
                                                }
                                                {" - "}
                                                {
                                                    loan.type
                                                }
                                                {" - "}
                                                {
                                                    loan.CustomerName
                                                }
                                                { " - "}
                                                {
                                                    loan.branch
                                                }
                                            </option>

                                        ))
                                    }

                                </select>

                            </div>

                            <div className="mb-4">

                                <label className="form-label fw-bold">
                                    Select Employee
                                </label>

                                <select
                                    className="form-select"
                                    value={employeeId}
                                    onChange={handleEmployeeChange}
                                    required
                                >
                                    <option value="">
                                        Select Employee
                                    </option>

                                    {
                                        employees.map((employee) => (
                                            <option
                                                key={employee.id}
                                                value={employee.id}
                                            >
                                                {employee.fullName}
                                                {" - "}
                                                {employee.designation}
                                                {" - "}
                                                {employee.branch.branch_name}
                                            </option>

                                        ))
                                    }
                                </select>
                            </div>

                            {
                                selectedLoan && (
                                    <div className="card border-primary mb-4">
                                        <div className="card-body">
                                            <h5 className="mb-3">
                                                Loan Details
                                            </h5>
                                            <p>
                                                <strong>
                                                    Customer:
                                                </strong>
                                                {
                                                    selectedLoan.customerName ??
                                                    selectedLoan.CustomerName
                                                }
                                            </p>
                                            <p>
                                                <strong>
                                                    Loan Type:
                                                </strong>
                                                {
                                                    selectedLoan.loanType ??
                                                    selectedLoan.type
                                                }
                                            </p>
                                            <p>
                                                <strong>
                                                    Amount:
                                                </strong>
                                                ₹
                                                {
                                                    selectedLoan.Amount ??
                                                    selectedLoan.loanAmount
                                                }
                                            </p>

                                            <p>
                                                <strong>
                                                    Branch:
                                                </strong>
                                                {
                                                    selectedLoan.branch
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
                                            <h5 className="mb-3">
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
                                className="btn btn-primary"
                            >
                                Assign Loan
                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </div>
    )
}

export default AssignLoan;
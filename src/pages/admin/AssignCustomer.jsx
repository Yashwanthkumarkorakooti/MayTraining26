import axios from "axios";
import { useEffect, useState } from "react";
import Navbar from "../../components/common/Navbar";
import AdminSidebar from "../../components/admin/adminSidebar";

const AssignCustomer = () => {

    const [customers, setCustomers] = useState([]);
    const [employees, setEmployees] = useState([]);

    const [customerId, setCustomerId] = useState("");
    const [employeeId, setEmployeeId] = useState("");

    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const config = {
        headers: {
            Authorization: "Bearer " +localStorage.getItem("token")
        }
    }

    useEffect(() => {
        fetchCustomers()
        fetchEmployees()
    }, [])

    const fetchCustomers = async () => {
        try {
            const response =
                await axios.get("http://localhost:8080/api/v1/customers",config)

            setCustomers(response.data)
            console.log(response.data)

        } catch (error) {
            console.log(error);
        }
    }

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

    const assignCustomer = async (e) => {
        e.preventDefault()

        setSuccessMsg("")
        setErrMsg("")

        try {
            const response =
                await axios.put(`http://localhost:8080/api/v1/admin/customers/${customerId}/assign-employee/${employeeId}`,{},config)
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
                            Assign Customer To Employee
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

                        <form onSubmit={assignCustomer}>
                            <div className="mb-4">
                                <label className="form-label">
                                    Customer
                                </label>
                                <select
                                    className="form-select"
                                    value={customerId}
                                    onChange={(e) =>setCustomerId(e.target.value)}
                                    required
                                >
                                    <option value="">
                                        Select Customer
                                    </option>
                                    {
                                        customers.map(customer => (
                                            <option
                                                key={customer.id}
                                                value={customer.id}
                                            >
                                                {customer.full_name} - {customer.branch.branch_name}
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
                                    onChange={(e) => setEmployeeId(e.target.value)}
                                    required
                                >

                                    <option value="">
                                        Select Employee
                                    </option>

                                    {
                                        employees.map(employee => (
                                            <option
                                                key={employee.id}
                                                value={employee.id}
                                            >
                                                {employee.fullName}- {customer.branch.branch_name}
                                            </option>

                                        ))
                                    }

                                </select>
                            </div>
                            <button
                                type="submit"
                                className="btn btn-primary"
                            >
                                Assign Customer
                            </button>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AssignCustomer;
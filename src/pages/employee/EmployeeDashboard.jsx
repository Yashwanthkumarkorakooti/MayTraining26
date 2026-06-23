import axios from "axios"
import { useEffect, useState } from "react"
import Loader from "../../components/common/Loader"
import Navbar from "../../components/common/Navbar"

import { Link } from "react-router-dom"
import EmployeeSidebar from "../../components/employee/EmployeeSidebar"
import EmployeeAnalytics from "./EmployeeAnalytics"


const EmployeeDashboard = () => {

    const [employee, setEmployee] = useState(null)
    const [Loading, setLoading] = useState(true)

    const employeeApi = "http://localhost:8080/api/v1/employees"

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }

    useEffect(() => {
        const getEmployeeDetails = async () => {
            try {
                const response = await axios.get(employeeApi, config)
                setEmployee(response.data)
                console.log(response.data)
            } catch (err) {
                console.log(err);
            } finally {
                setLoading(false)
            }
        }
        getEmployeeDetails()
    }, [])

    if (Loading) return <Loader />

    return (
        <div>

            <Navbar />
            <EmployeeSidebar />

            <div className="page-content">
                <div className="mb-4">
                    <h2> Welcome,{employee.fullName}</h2>
                    <p className="text-muted">Employee Dashboard</p>
                </div>

                <div className="row">
                    <div className="col-lg-4 col-md-6 mb-4">
                        <div className="card dashboard-card bg-primary-card shadow border-0">
                            <div className="card-body">
                                <h6 className="text-light"> Customers Handled </h6>
                                <h2 className="text-white fw-bold"> { employee.customersHandled } </h2>
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-4 col-md-6 mb-4">
                        <div className="card dashboard-card bg-success-card shadow border-0">
                            <div className="card-body">
                                <h6 className="text-light"> Accounts Approved </h6>
                                <h2 className="text-white fw-bold">{employee.accountsApproved} </h2>
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-4 col-md-6 mb-4">
                        <div className="card dashboard-card bg-warning-card shadow border-0">
                            <div className="card-body">
                                <h6 className="text-light">  Loans Reviewed </h6>
                                <h2 className="text-white fw-bold">
                                    { employee.loansReviewed }
                                </h2>
                            </div>
                        </div>
                    </div>

                </div>

                <div className="card shadow border-0">
                    <div className="card-body">
                        <h4 className="mb-4">  Quick Actions  </h4>

                        <div className="row">
                            <div className="col-md-3 mb-3">
                                <Link to="/employee/add-customer" className="btn btn-primary w-100">
                                    Add Customer
                                </Link>
                            </div>

                            <div className="col-md-3 mb-3">
                                <Link to="/employee/search-customer" className="btn btn-success w-100">
                                    Search Customer
                                </Link>
                            </div>

                            <div className="col-md-3 mb-3">
                                <Link  to="/employee/analytics" className="btn btn-warning w-100">
                                    Analytics
                                </Link>
                            </div>

                            <div className="col-md-3 mb-3">
                                <Link to="/employee/loan-review" className="btn btn-danger w-100" >
                                    Loan Review
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="mt-5">

                    <div className="card shadow border-0">
                        <div className="card-body">
                            <h3 className="mb-4"> Employee Analytics </h3>
                            <EmployeeAnalytics />
                        </div>
                    </div>
                </div>

                
            </div>
        </div>
    )
}

export default EmployeeDashboard
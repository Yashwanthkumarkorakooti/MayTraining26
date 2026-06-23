import axios from "axios"
import { useEffect, useState } from "react"
import Navbar from "../../components/common/Navbar"
import AdminSidebar from "../../components/admin/adminSidebar"
import { Link } from "react-router-dom"

const Employeelist = () => {

    const [employee, setEmployee] = useState([])
    const [errMsg, setErrMsg] = useState('')
    const employeeApi = 'http://localhost:8080/api/v1/admin/employees'

    const [page, setPage] = useState(0)
    const [size, setSize] = useState(10)
    const [search, setSearch] = useState('')
    const [designation, setDesignation] = useState('')
    const [sortSalary, setSortSalary] = useState('')

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }

    useEffect(() => {
        const getAllEmployees = async () => {
            try {
                const response = await axios.get(`${employeeApi}?page=${page}&size=${size}`, config)

                setEmployee(response.data)
                setErrMsg('')
                console.log(response.data)

            } catch (error) {
                console.log(error.response?.data);

                setErrMsg(error.response?.message ||
                    error.response?.data?.message ||
                    error.response?.data ||
                    "Failed To Create Employee"
                )
            }
        }
        getAllEmployees()
    }, [page])

    const resetFilters = () => {
        setSearch('')
        setDesignation('')
        setSortSalary('')
    }

    let filteredEmployees = [...employee].filter(emp =>
        emp.fullName.toLowerCase().includes(search.toLowerCase()) ||
        emp.employeeCode.toLowerCase().includes(search.toLowerCase()) ||
        emp.branchName.toLowerCase().includes(search.toLowerCase())
    )

    if (designation) {
        filteredEmployees = filteredEmployees.filter(
            emp => emp.designation === designation
        )
    }

    if (sortSalary === 'asc') {
        filteredEmployees.sort((a, b) => a.salary - b.salary)
    }

    if (sortSalary === 'desc') {
        filteredEmployees.sort((a, b) => b.salary - a.salary)
    }


    return (
        <div>
            <Navbar />
            <AdminSidebar />

            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body">
                        <h2 className="mb-4"> Employees </h2>

                        {
                            errMsg && (
                                <div className="alert alert-danger"> {errMsg} </div>
                            )
                        }

                        <div className="card mb-4 shadow-sm">
                            <div className="card-body">
                                <div className="row g-3">

                                    <div className="col-md-4">
                                        <input type="text" className="form-control"
                                            placeholder="Search Name / Code / Branch" value={search}
                                            onChange={(e) => setSearch(e.target.value)} />
                                    </div>

                                    <div className="col-md-3">
                                        <select className="form-select" value={designation}
                                            onChange={(e) => setDesignation(e.target.value)}>
                                            <option value=""> All Designations </option>
                                            <option value="MANAGER"> MANAGER </option>
                                            <option value="LOAN_OFFICER"> LOAN_OFFICER </option>
                                            <option value="CUSTOMER_SUPPORT"> CUSTOMER_SUPPORT </option>
                                            <option value="ACCOUNTANT"> ACCOUNTANT </option>
                                            <option value="CASHIER"> CASHIER</option>

                                        </select>
                                    </div>

                                    <div className="col-md-3">
                                        <select className="form-select" value={sortSalary}
                                            onChange={(e) => setSortSalary(e.target.value)}>
                                            <option value=""> Sort Salary</option>
                                            <option value="asc"> Salary Low to High</option>
                                            <option value="desc"> Slary High to Low</option>
                                        </select>
                                    </div>

                                </div>
                            </div>
                        </div>


                        <div className="table-responsive">
                            <table className="table table-havor">
                                <thead>
                                    <tr>
                                        <th>Code</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Branch</th>
                                        <th>Designation</th>
                                        <th>Salary</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        filteredEmployees.length > 0 ? (
                                            filteredEmployees.map((emp, idx) => (
                                                <tr key={emp.employeeId}>
                                                    <td> {emp.employeeCode} </td>
                                                    <td> {emp.fullName} </td>
                                                    <td> {emp.email} </td>
                                                    <td> {emp.branchName} </td>
                                                    <td> {emp.designation} </td>
                                                    <td> ₹{emp.salary} </td>
                                                    <td>
                                                        <Link
                                                            to={`/admin/transfer-employee/${emp.employeeId}`}>
                                                            Transfer
                                                        </Link>

                                                    </td>
                                                </tr>
                                            ))
                                        ) : (
                                            <tr>
                                                <td colSpan='8' className="text-center"> No Employees Found </td>
                                            </tr>
                                        )

                                    }
                                </tbody>
                            </table>
                        </div>

                        <div className="d-flex justify-content-center gap-2 mt-4">

                            <button className="btn btn-outline-primary"
                                disabled={page === 0}
                                onClick={() => setPage(page - 1)}>
                                Previous
                            </button>

                            <span className="align-self-center fw-bold">
                                Page {page + 1}
                            </span>

                            <button
                                className="btn btn-outline-primary"
                                onClick={() => setPage(page + 1)}>
                                Next
                            </button>

                        </div>

                    </div>
                </div>
            </div>
        </div>
    )
}

export default Employeelist
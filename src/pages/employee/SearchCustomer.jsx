import axios from "axios";
import { useEffect, useState } from "react";
import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";
import { Link } from "react-router-dom";

const SearchCustomer = () => {

    const [customers, setCustomers] = useState([]);
    const [errMsg, setErrMsg] = useState("");

    const [page, setPage] = useState(0);
    const [size] = useState(10);

    const [searchText, setSearchText] = useState("");
    const [kycFilter, setKycFilter] = useState("");
    const [riskFilter, setRiskFilter] = useState("");

    const searchApi =
        "http://localhost:8080/api/v1/employees/assign-customers";

    const config = {
        headers: {
            Authorization: "Bearer " +localStorage.getItem("token")
        }
    }

    const getCustomers = async () => {
        try {
            const response =
                await axios.get(`${searchApi}?page=${page}&size=${size}`,config)

            setCustomers(response.data)
            setErrMsg("")
            console.log(response.data)

        } catch (err) {
            console.log(err)
            setErrMsg(
                err.response?.data?.message ||
                err.response?.data ||
                "Failed To Load Customers"
            )
        }
    }

    useEffect(() => {
        // console.log("Current Page:", page);
        getCustomers();
    }, [page]);

    const resetFilters = () => {
        setSearchText("");
        setKycFilter("");
        setRiskFilter("");
    };

    let filteredCustomers =[...customers].filter(customer =>
        customer.customerName?.toLowerCase().includes(searchText.toLowerCase()) ||
        customer.email?.toLowerCase().includes(searchText.toLowerCase()) ||
        customer.phone?.includes(searchText)
        )

    if (kycFilter) {
        filteredCustomers = filteredCustomers.filter(
                customer => customer.kycStatus === kycFilter
            )
    }

    if (riskFilter) {
        filteredCustomers = filteredCustomers.filter(
                customer => customer.riskLevel === riskFilter
            )
    }

    return (
        <div>

            <Navbar />
            <EmployeeSidebar />

            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body">

                        <h2 className="mb-4">
                            Search Customers
                        </h2>
                        {
                            errMsg && (
                                <div className="alert alert-danger">
                                    {errMsg}
                                </div>
                            )
                        }

                        <div className="card mb-4 shadow-sm">
                            <div className="card-body">
                                <div className="row g-3">
                                    <div className="col-md-4">
                                        <input
                                            type="text"
                                            className="form-control"
                                            placeholder="Search Name / Email / Phone"
                                            value={searchText}
                                            onChange={(e) => setSearchText(e.target.value)}/>
                                    </div>

                                    <div className="col-md-3">
                                        <select
                                            className="form-select"
                                            value={kycFilter}
                                            onChange={(e) => setKycFilter(e.target.value)}>

                                            <option value="">
                                                All KYC Status
                                            </option>

                                            <option value="VERIFIED">
                                                VERIFIED
                                            </option>

                                            <option value="PENDING">
                                                PENDING
                                            </option>

                                            <option value="REJECTED">
                                                REJECTED
                                            </option>

                                        </select>

                                    </div>

                                    <div className="col-md-3">

                                        <select
                                            className="form-select"
                                            value={riskFilter}
                                            onChange={(e) => setRiskFilter(e.target.value)}>

                                            <option value="">
                                                All Risk Levels
                                            </option>

                                            <option value="LOW">
                                                LOW
                                            </option>

                                            <option value="MEDIUM">
                                                MEDIUM
                                            </option>

                                            <option value="HIGH">
                                                HIGH
                                            </option>

                                        </select>

                                    </div>

                                    <div className="col-md-2">

                                        <button
                                            className="btn btn-secondary w-100"
                                            onClick={resetFilters}>
                                            Reset
                                        </button>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="table-responsive">
                            <table className="table table-hover align-middle">
                                <thead className="table-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Loans</th>
                                        <th>Accounts</th>
                                        <th>KYC</th>
                                        <th>Risk</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        filteredCustomers.length > 0  ? (
                                                filteredCustomers.map(customer => (
                                                    <tr key={customer.customerId}>
                                                        <td>
                                                            {customer.customerId}
                                                        </td>

                                                        <td>
                                                            {customer.customerName}
                                                        </td>

                                                        <td>
                                                            {customer.email}
                                                        </td>

                                                        <td>
                                                            {customer.phone}
                                                        </td>

                                                        <td>
                                                            <span className="badge bg-primary">
                                                                {customer.activeLoans}
                                                            </span>
                                                        </td>

                                                        <td>
                                                            <span className="badge bg-info">
                                                                {customer.totalAccounts}
                                                            </span>

                                                        </td>

                                                        <td>
                                                            {customer.kycStatus === "VERIFIED"? (
                                                                        <span className="badge bg-success">
                                                                            VERIFIED
                                                                        </span>
                                                                    ) : (
                                                                        <Link
                                                                            to={`/employee/update-kyc/${customer.customerId}`}
                                                                            className="badge bg-warning text-dark text-decoration-none">
                                                                            {customer.kycStatus}
                                                                        </Link>
                                                                    )
                                                            }

                                                        </td>

                                                        <td>

                                                            <span
                                                                className={
                                                                    customer.riskLevel === "HIGH"
                                                                        ? "badge bg-danger": customer.riskLevel === "MEDIUM"
                                                                        ? "badge bg-warning text-dark" : "badge bg-success"
                                                                }>
                                                                {customer.riskLevel}
                                                            </span>
                                                        </td>

                                                        {/* <td>

                                                            <div className="d-flex gap-2">

                                                                <Link
                                                                    to={`/employee/customer/${customer.customerId}`}
                                                                    className="btn btn-primary btn-sm"
                                                                >
                                                                    View
                                                                </Link>

                                                                {
                                                                    customer.kycStatus !== "VERIFIED" &&
                                                                    (
                                                                        <Link
                                                                            to={`/employee/update-kyc/${customer.customerId}`}
                                                                            className="btn btn-warning btn-sm"
                                                                        >
                                                                            KYC
                                                                        </Link>
                                                                    )
                                                                }

                                                            </div>

                                                        </td> */}

                                                    </tr>
                                                ))
                                            ) : (
                                                <tr>

                                                    <td
                                                        colSpan="9"
                                                        className="text-center"
                                                    >
                                                        No Customers Found
                                                    </td>

                                                </tr>
                                            )
                                    }

                                </tbody>

                            </table>

                        </div>

                        {/* Pagination */}

                        <div className="d-flex justify-content-center gap-2 mt-4">

                            <button
                                className="btn btn-outline-primary"
                                disabled={page === 0}
                                onClick={() =>
                                    setPage(page - 1)
                                }
                            >
                                Previous
                            </button>

                            <span className="align-self-center fw-bold">
                                Page {page + 1}
                            </span>

                            <button
                                className="btn btn-outline-primary"
                                onClick={() =>
                                    setPage(page + 1)
                                }
                            >
                                Next
                            </button>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SearchCustomer;
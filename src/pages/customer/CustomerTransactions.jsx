import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";
import Loader from "../../components/common/Loader";

const CustomerTransactions = () => {

    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [errMsg, setErrMsg] = useState("");

    const [page, setPage] = useState(0);
    const [size] = useState(10);

    const [search, setSearch] = useState("");
    const [type, setType] = useState("");
    const [status, setStatus] = useState("");

    const transactionsApi =
        "http://localhost:8080/api/v1/customers/transactions";

    useEffect(() => {
        fetchTransactions();
    }, [page, search, type, status]);

    const fetchTransactions = async () => {

        try {

            // setLoading(true);

            const config = {
                headers: {
                    Authorization:
                        "Bearer " +
                        localStorage.getItem("token")
                },
                params: {
                    page,
                    size,
                    search,
                    type,
                    status
                }
            };

            const response =
                await axios.get(
                    transactionsApi,
                    config
                );

            setTransactions(response.data);
            setErrMsg("");

            console.log(response.data);

        } catch (error) {

            console.log(error);

            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Failed To Load Transactions"
            );

        } finally {

            setLoading(false);

        }
    };

    const resetFilters = () => {

        setSearch("");
        setType("");
        setStatus("");
        setPage(0);

    };

    if (loading) {
        return <Loader />;
    }

    return (
        <div>

            <Navbar />
            <CustomerSidebar />

            <div className="page-content">

                <div className="card shadow border-0">

                    <div className="card-body">

                        <h2 className="mb-4">
                            Transaction History
                        </h2>

                        {
                            errMsg && (
                                <div className="alert alert-danger">
                                    {errMsg}
                                </div>
                            )
                        }

                        {/* Filters */}

                        <div className="card mb-4 shadow-sm">

                            <div className="card-body">

                                <div className="row g-3">

                                    <div className="col-md-4">

                                        <input
                                            type="text"
                                            className="form-control"
                                            placeholder="Search Beneficiary"
                                            value={search}
                                            onChange={(e) =>
                                                setSearch(
                                                    e.target.value
                                                )
                                            }
                                        />

                                    </div>

                                    <div className="col-md-3">

                                        <select
                                            className="form-select"
                                            value={type}
                                            onChange={(e) =>
                                                setType(
                                                    e.target.value
                                                )
                                            }
                                        >

                                            <option value="">
                                                All Types
                                            </option>

                                            <option value="DEPOSIT">
                                                DEPOSIT
                                            </option>

                                            <option value="WITHDRAWAL">
                                                WITHDRAWAL
                                            </option>

                                            <option value="TRANSFER">
                                                TRANSFER
                                            </option>

                                        </select>

                                    </div>

                                    <div className="col-md-3">

                                        <select
                                            className="form-select"
                                            value={status}
                                            onChange={(e) =>
                                                setStatus(
                                                    e.target.value
                                                )
                                            }
                                        >

                                            <option value="">
                                                All Status
                                            </option>

                                            <option value="SUCCESS">
                                                SUCCESS
                                            </option>

                                            <option value="FAILED">
                                                FAILED
                                            </option>

                                        </select>

                                    </div>

                                    <div className="col-md-2">

                                        <button
                                            className="btn btn-secondary w-100"
                                            onClick={resetFilters}
                                        >
                                            Reset
                                        </button>

                                    </div>

                                </div>

                            </div>

                        </div>

                        {/* Table */}

                        <div className="table-responsive">

                            <table className="table table-hover">

                                <thead className="table-light">

                                    <tr>

                                        <th>Reference</th>
                                        <th>Amount</th>
                                        <th>Type</th>
                                        <th>Beneficiary</th>
                                        <th>Status</th>
                                        <th>Date</th>

                                    </tr>

                                </thead>

                                <tbody>

                                    {
                                        transactions.length > 0 ?

                                            transactions.map(
                                                (transaction) => (

                                                    <tr
                                                        key={
                                                            transaction.reference
                                                        }
                                                    >

                                                        <td>
                                                            {
                                                                transaction.reference
                                                            }
                                                        </td>

                                                        <td>
                                                            ₹
                                                            {
                                                                transaction.amount
                                                            }
                                                        </td>

                                                        <td>
                                                            {
                                                                transaction.transactionType
                                                            }
                                                        </td>

                                                        <td>
                                                            {
                                                                transaction.beneficiaryName ||
                                                                "-"
                                                            }
                                                        </td>

                                                        <td>

                                                            <span
                                                                className={
                                                                    transaction.status === "SUCCESS"
                                                                        ? "badge bg-success"
                                                                        : "badge bg-danger"
                                                                }
                                                            >
                                                                {
                                                                    transaction.status
                                                                }
                                                            </span>

                                                        </td>

                                                        <td>
                                                            {
                                                                new Date(
                                                                    transaction.transactionDate
                                                                ).toLocaleDateString()
                                                            }
                                                        </td>

                                                    </tr>

                                                )
                                            )

                                            :

                                            <tr>

                                                <td
                                                    colSpan="6"
                                                    className="text-center"
                                                >
                                                    No Transactions Found
                                                </td>

                                            </tr>
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
    );
};

export default CustomerTransactions;
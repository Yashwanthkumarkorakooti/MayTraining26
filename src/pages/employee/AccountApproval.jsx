import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";

const AccountApproval = () => {

    const [accounts, setAccounts] = useState([]);
    const [rejectReason, setRejectReason] = useState("");
    const [selectedAccount, setSelectedAccount] = useState(null);

    const [customerName, setCustomerName] = useState("");
    const [phone, setPhone] = useState("");
    const [accountType, setAccountType] = useState("");
    const [status, setStatus] = useState("");

    const [page, setPage] = useState(0);
    const [size] = useState(10);

    const accountApi = "http://localhost:8080/api/v1/employees/assigned-accounts";

    useEffect(() => {
        fetchAssignedAccounts()
        console.log("Page Changed:", page)
    }, [page, customerName, phone, accountType, status]);

    const fetchAssignedAccounts = async () => {
        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }

            const response = await axios.get(`${accountApi}?page=${page}&size=${size}`,
                {
                    headers: {
                        Authorization:"Bearer " +localStorage.getItem("token"),
                    },
                    params: {
                        customerName:customerName || null,
                        phone:phone || null,
                        accountType:accountType || null,
                        status:status || null,
                        page,
                        size
                    }
                }
            );

            setAccounts(response.data);
            console.log(response.data)
        } catch (error) {
            console.log(error);
            console.log(error.response?.data);
            console.log(error.response?.status);
        }
    }

    const approveAccount = async (accountId) => {
        try {
            const config = {
                headers: {
                    Authorization: "Bearer " +localStorage.getItem("token")
                }
            }

            await axios.put(`http://localhost:8080/api/v1/customer/accounts/approve/${accountId}`,{},config)

            fetchAssignedAccounts()
        } catch (error) {
            console.log(error)
            console.log(error.response?.data)
        }
    };

    const rejectAccount = async () => {
        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }

            const payload = {
                reason: rejectReason,
            }

            await axios.put(`http://localhost:8080/api/v1/customer/accounts/reject/${selectedAccount}`,payload,config);

            setRejectReason("");
            setSelectedAccount(null);

            fetchAssignedAccounts();
        } catch (error) {
            console.log(error);
            console.log(error.response?.data);
        }
    };

    return (
        <div>
            <Navbar />
            <EmployeeSidebar />

            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body">

                        <h2 className="mb-4">
                            Accounts Approval
                        </h2>

                        <div className="row mb-4">
                            <div className="col-md-3">
                                <input
                                    type="text" className="form-control" placeholder="Customer Name"
                                    value={customerName}
                                    onChange={(e) => setCustomerName(e.target.value)}/>
                            </div>

                            <div className="col-md-2">
                                <input
                                    type="text" className="form-control" placeholder="Phone"
                                    value={phone}
                                    onChange={(e) =>setPhone(e.target.value)}/>
                            </div>

                            <div className="col-md-2">
                                <select
                                    className="form-select" value={accountType}
                                    onChange={(e) => setAccountType(e.target.value)}>

                                    <option value=""> All Types </option>
                                    <option value="SAVINGS"> SAVINGS </option>
                                    <option value="CURRENT"> CURRENT </option>
                                    <option value="FIXED_DEPOSIT"> FIXED DEPOSIT </option>

                                </select>
                            </div>

                            <div className="col-md-2">
                                <select
                                    className="form-select" value={status}
                                    onChange={(e) =>setStatus(e.target.value )}>

                                    <option value="">All Status </option>
                                    <option value="PENDING">PENDING </option>
                                    <option value="ACTIVE"> ACTIVE </option>
                                    <option value="REJECTED"> REJECTED </option>
                                    <option value="FROZEN"> FROZEN </option>

                                </select>
                            </div>

                            <div className="col-md-3">
                                <button
                                    className="btn btn-secondary"
                                    onClick={() => {
                                        setCustomerName("");
                                        setPhone("");
                                        setAccountType("");
                                        setStatus("");
                                        setPage(0);
                                    }}>
                                    Clear Filters
                                </button>
                            </div>

                        </div>

                        <div className="table-responsive">
                            <table className="table table-hover">

                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Customer</th>
                                        <th>Account Number </th>
                                        <th>Branch </th>
                                        <th>Type</th>
                                        <th>KYC Status </th>
                                        <th>Email</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                        <th>DOCs</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    {accounts.length > 0 ? (accounts.map((account) => (
                                        <tr key={account.accountId}>
                                            <td>{account.accountId} </td>
                                            <td> {account.customerName}</td>
                                            <td> {account.accountNumber}</td>
                                            <td> {account.branchName} </td>
                                            <td> {account.accountType}</td>
                                            <td> {account.kycStatus}</td>
                                            <td>{account.email} </td>
                                            <td>
                                                <span
                                                    className={`badge ${account.accountStatus === "PENDING"
                                                        ? "bg-warning text-dark" : account.accountStatus === "ACTIVE"
                                                        ? "bg-success": account.accountStatus === "REJECTED"
                                                        ? "bg-danger": account.accountStatus === "FROZEN"
                                                        ? "bg-dark" : "bg-secondary"}`}>
                                                    {account.accountStatus}
                                                </span>
                                            </td>

                                            <td>
                                                {account.accountStatus === "PENDING" ? (
                                                    <>
                                                        <button
                                                            className="btn btn-success btn-sm me-2"
                                                            onClick={() =>approveAccount(account.accountId)}>
                                                            Approve
                                                        </button>

                                                        <button
                                                            className="btn btn-danger btn-sm"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#rejectModal"
                                                            onClick={() =>setSelectedAccount(account.accountId)}>
                                                            Reject
                                                        </button>
                                                    </>
                                                ) : (
                                                    <span className="badge bg-secondary">
                                                        Already Processed
                                                    </span>
                                                )}
                                            </td>
                                            <td>
                                                {
                                                    account.documentPath &&
                                                        account.documentPath !== "NA" ? (
                                                            <div className="d-flex gap-1">

                                                                <a
                                                                    href={`http://localhost:5173/images/${account.documentPath}/aadhar.jpg`}
                                                                    target="_blank"
                                                                    rel="noreferrer"
                                                                    className="btn btn-primary btn-sm"
                                                                >
                                                                    Aadhar
                                                                </a>

                                                                <a
                                                                    href={`http://localhost:5173/images/${account.documentPath}/pan.jpg`}
                                                                    target="_blank"
                                                                    rel="noreferrer"
                                                                    className="btn btn-success btn-sm"
                                                                >
                                                                    PAN
                                                                </a>

                                                                <a
                                                                    href={`http://localhost:5173/images/${account.documentPath}/photo.jpg`}
                                                                    target="_blank"
                                                                    rel="noreferrer"
                                                                    className="btn btn-warning btn-sm"
                                                                >
                                                                    Photo
                                                                </a>

                                                            </div>
                                                        

                                                    ) : (

                                                        <span className="badge bg-danger">
                                                            Missing
                                                        </span>

                                                    )
                                                }
                                            </td>
                                        </tr>
                                    )))  : 
                                    (<tr>
                                                <td colSpan='8' className="text-center"> No Accounts Found </td>
                                            </tr>)
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

            {/* Reject Modal */}

            <div
                className="modal fade"
                id="rejectModal"
                tabIndex="-1"
            >
                <div className="modal-dialog">
                    <div className="modal-content">

                        <div className="modal-header">
                            <h5>
                                Reject Account
                            </h5>

                            <button
                                type="button"
                                className="btn-close"
                                data-bs-dismiss="modal"
                            ></button>
                        </div>

                        <div className="modal-body">
                            <textarea
                                className="form-control"
                                placeholder="Enter Reason"
                                value={rejectReason}
                                onChange={(e) =>
                                    setRejectReason(
                                        e.target.value
                                    )
                                }
                            />
                        </div>

                        <div className="modal-footer">

                            <button
                                type="button"
                                className="btn btn-secondary"
                                data-bs-dismiss="modal"
                            >
                                Cancel
                            </button>

                            <button
                                className="btn btn-danger"
                                onClick={rejectAccount}
                                data-bs-dismiss="modal"
                            >
                                Reject
                            </button>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AccountApproval;
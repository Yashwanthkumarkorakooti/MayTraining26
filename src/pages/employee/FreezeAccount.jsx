import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";

const FreezeAccount = () => {

    const [accounts, setAccounts] = useState([]);
    const [filteredAccounts, setFilteredAccounts] = useState([]);

    const [search, setSearch] = useState("");

    const [responseData, setResponseData] = useState(null);
    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const accountApi =
        "http://localhost:8080/api/v1/employees/assigned-accounts";

    const config = {
        headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
        }
    }

    useEffect(() => {
        fetchAccounts();
    }, []);

    useEffect(() => {
        const filtered = accounts.filter(account => account.customerName?.toLowerCase().includes(search.toLowerCase()) ||
            account.accountNumber?.toLowerCase().includes(search.toLowerCase()) ||
            account.email?.toLowerCase().includes(search.toLowerCase())
        )
        setFilteredAccounts(filtered);
    }, [search, accounts]);

    const fetchAccounts = async () => {
        try {
            const response = await axios.get(accountApi,config)
            console.log(response.data)
            setAccounts(response.data)
            setFilteredAccounts(response.data)
        } catch (error) {
            console.log(error);
            setErrMsg(
                error.response?.data?.message ||
                "Failed to Load Accounts"
            )
        }
    }

    const freezeAccount = async (accountId) => {

        setErrMsg("")
        setSuccessMsg("")
        setResponseData(null)
        try {

            const response = await axios.put(`http://localhost:8080/api/v1/employees/accounts/freeze/${accountId}`,{},config)
            setResponseData(response.data)
            setSuccessMsg(
                response.data.reason ||
                "Account Frozen Successfully"
            )
            fetchAccounts()

        } catch (error) {
            console.log(error);
            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Failed to Freeze Account"
            )
        }
    }

    const activateAccount = async (accountId) => {

        setErrMsg("");
        setSuccessMsg("");
        setResponseData(null);

        try {

            const response =
                await axios.put(`http://localhost:8080/api/v1/employees/accounts/activate/${accountId}`,{},config)
            setResponseData(response.data);
            setSuccessMsg("Account Activated Successfully")
            fetchAccounts();
        } catch (error) {
            console.log(error);
            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Failed to Activate Account"
            )
        }
    }

    return (
        <div>

            <Navbar />
            <EmployeeSidebar />

            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body">
                        <h2 className="mb-4">
                            Freeze / Activate Accounts
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

                        <div className="row mb-4">
                            <div className="col-md-6">
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Search Customer / Account Number / Email"
                                    value={search}
                                    onChange={(e) =>setSearch(e.target.value) }/>
                            </div>
                        </div>

                        <div className="table-responsive">
                            <table className="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Customer</th>
                                        <th>Email</th>
                                        <th>Account Type</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        filteredAccounts.length > 0 ?
                                            filteredAccounts.map(account => (
                                                <tr key={account.accountId}>
                                                    <td>
                                                        {
                                                            account.accountId
                                                        }
                                                    </td>
                                                    <td>
                                                        {
                                                            account.customerName
                                                        }
                                                    </td>
                                                    <td>
                                                        {
                                                            account.email
                                                        }
                                                    </td>
                                                    <td>
                                                        {
                                                            account.accountType
                                                        }
                                                    </td>
                                                    <td>
                                                        <span
                                                            className={
                                                                account.accountStatus === "ACTIVE"
                                                                    ? "badge bg-success" : account.accountStatus === "FROZEN"
                                                                    ? "badge bg-danger" : account.accountStatus === "PENDING"
                                                                    ? "badge bg-warning text-dark" : "badge bg-secondary" }>
                                                            {
                                                                account.accountStatus
                                                            }
                                                        </span>
                                                    </td>
                                                    <td>
                                                        {
                                                            account.accountStatus === "ACTIVE" && (
                                                                <button
                                                                    className="btn btn-danger btn-sm"
                                                                    onClick={() => freezeAccount(account.accountId)}>
                                                                    Freeze
                                                                </button>
                                                            )
                                                        }
                                                        {
                                                            account.accountStatus === "FROZEN" && (
                                                                <button
                                                                    className="btn btn-success btn-sm"
                                                                    onClick={() => activateAccount(account.accountId)}>
                                                                    Activate
                                                                </button>
                                                            )
                                                        }
                                                        {
                                                            account.accountStatus !== "ACTIVE" &&
                                                            account.accountStatus !== "FROZEN" && (
                                                                <span className="badge bg-secondary">
                                                                    No Action
                                                                </span>
                                                            )
                                                        }
                                                    </td>
                                                </tr>
                                            ))
                                            :
                                            <tr>
                                                <td colSpan="6"
                                                    className="text-center" >
                                                    No Accounts Found
                                                </td>
                                            </tr>
                                    }

                                </tbody>
                            </table>
                        </div>
                        {
                            responseData && (
                                <div className="card border-0 shadow mt-4">
                                    <div className="card-body">
                                        <h4
                                            className={responseData.newStatus === "FROZEN" ? "text-danger": "text-success"}>
                                            Account Updated Successfully
                                        </h4>
                                        <hr />
                                        <p>
                                            <strong>
                                                Account :
                                            </strong>
                                            {
                                                responseData.customerAccount
                                            }
                                        </p>
                                        <p>
                                            <strong>
                                                Customer :
                                            </strong>
                                            {
                                                responseData.customerName
                                            }
                                        </p>
                                        <p>
                                            <strong>
                                                Previous Status :
                                            </strong>
                                            <span className="badge bg-secondary">
                                                {
                                                    responseData.oldStatus
                                                }
                                            </span>
                                        </p>
                                        <p>
                                            <strong>
                                                Current Status :
                                            </strong>
                                            <span
                                                className={
                                                    responseData.newStatus === "FROZEN"
                                                        ? "badge bg-danger"
                                                        : "badge bg-success"}>
                                                {
                                                    responseData.newStatus
                                                }
                                            </span>
                                        </p>
                                        <p>
                                            <strong>
                                                Message :
                                            </strong>
                                            {
                                                responseData.reason
                                            }
                                        </p>
                                    </div>
                                </div>
                            )
                        }

                    </div>
                </div>
            </div>
        </div>
    )
}

export default FreezeAccount;
import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";

const CloseAccount = () => {

    const [accounts, setAccounts] = useState([]);
    const [selectedAccountId, setSelectedAccountId] = useState("");

    const [reason, setReason] = useState("");

    const [responseData, setResponseData] = useState(null);
    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const accountsApi =
        "http://localhost:8080/api/v1/customer-accounts/accounts";

    const config = {
        headers: {
            Authorization:"Bearer " +localStorage.getItem("token")
        }
    }

    useEffect(() => {
        fetchAccounts()
    }, [])

    const fetchAccounts = async () => {
        try {
            const response =
                await axios.get(accountsApi,config)
            setAccounts(response.data);

        } catch (error) {
            console.log(error);
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        setErrMsg("")
        setSuccessMsg("")
        setResponseData(null)

        if (!selectedAccountId) {
            setErrMsg("Please select account")
            return
        }

        try {

            const response =
                await axios.post(`http://localhost:8080/api/v1/customer/accounts/close-request/${selectedAccountId}`,{},config)
            setResponseData(response.data);
            setSuccessMsg("Account closure request submitted successfully")

            fetchAccounts();

        } catch (error) {
            console.log(error);

            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Unable to Close Account"
            );
        }
    };

    return (
        <div>

            <Navbar />
            <CustomerSidebar />

            <div className="page-content">
                <div className="card shadow border-0 form-card">
                    <div className="card-body">
                        <h2 className="mb-4">
                            Close Account
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

                        <div className="alert alert-warning">
                            <strong>
                                Before Closing Account:
                            </strong>

                            <ul className="mb-0 mt-2">
                                <li>
                                    Balance must be ₹0
                                </li>

                                <li>
                                    No Active Loans
                                </li>

                                <li>
                                    No Pending Transactions
                                </li>
                            </ul>

                        </div>

                        <form onSubmit={handleSubmit}>
                            <div className="card bg-light border-0 mb-4">
                                <div className="card-body">
                                    <h5 className="mb-3">
                                        Select Account
                                    </h5>
                                    {
                                        accounts.length > 0 ?accounts.map(account => (
                                                <div
                                                    key={account.accountId}
                                                    className={`card mb-3 ${
                                                        selectedAccountId === account.accountId
                                                            ? "border-danger border-3"
                                                            : ""
                                                    }`}
                                                >
                                                    <div className="card-body">
                                                        <div className="form-check">
                                                            <input
                                                                type="radio"
                                                                className="form-check-input"
                                                                name="account"
                                                                value={account.accountId}
                                                                checked={selectedAccountId ===account.accountId}
                                                                onChange={() =>setSelectedAccountId(account.accountId)}
                                                            />

                                                            <label className="form-check-label ms-2">
                                                                <h6>
                                                                    {
                                                                        account.accountNumber
                                                                    }
                                                                </h6>
                                                                <p className="mb-1">
                                                                    {
                                                                        account.accountType
                                                                    }
                                                                </p>
                                                                <p className="mb-1">
                                                                    Balance :
                                                                    ₹{
                                                                        account.balance
                                                                    }
                                                                </p>

                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            ))
                                            :
                                            <div className="alert alert-warning">
                                                No Accounts Found
                                            </div>
                                    }

                                </div>
                            </div>

                            <div className="mb-4">
                                <label className="form-label fw-bold">
                                    Reason For Closure
                                </label>

                                <textarea
                                    className="form-control"
                                    rows="3"
                                    placeholder="Optional"
                                    value={reason}
                                    onChange={(e) =>setReason(e.target.value)}
                                />

                            </div>

                            <button
                                type="submit"
                                className="btn btn-danger submit-btn"
                            >
                                Request Closure
                            </button>

                        </form>
                        {
                            responseData && (
                                <div className="card mt-4 border-success">
                                    <div className="card-body">
                                        <h4 className="text-success">
                                            Closure Request Submitted
                                        </h4>
                                        <hr />
                                        <p>
                                            <strong>
                                                Account Number:
                                            </strong>
                                            {
                                                responseData.accountNumber
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Customer:
                                            </strong>
                                            {
                                                responseData.customerName
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Status:
                                            </strong>
                                            {
                                                responseData.status
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Message:
                                            </strong>
                                            {
                                                responseData.message
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

export default CloseAccount;
import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";

const DepositMoney = () => {

    const [accounts, setAccounts] = useState([]);
    const [selectedAccount, setSelectedAccount] = useState("");

    const [amount, setAmount] = useState("");
    const [remarks, setRemarks] = useState("");

    const [responseData, setResponseData] = useState(null);
    const [errMsg, setErrMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");

    const [searchParams] = useSearchParams();

    const accountId =
        searchParams.get("accountId");

    const depositApi =
        "http://localhost:8080/api/v1/accounts/employee/deposit";

    const accountApi =
        "http://localhost:8080/api/v1/customer-accounts/accounts";

    useEffect(() => {
        fetchAccounts();
    }, []);

    useEffect(() => {

        if (accounts.length > 0 && accountId) {

            const account =
                accounts.find(
                    a => a.accountId == accountId
                );

            if (account) {
                setSelectedAccount(
                    account.accountNumber
                );
            }
        }

    }, [accounts, accountId]);

    const fetchAccounts = async () => {

        try {

            const config = {
                headers: {
                    Authorization:
                        "Bearer " +
                        localStorage.getItem("token")
                }
            };

            const response =
                await axios.get(
                    accountApi,
                    config
                );

            console.log(response.data);

            setAccounts(response.data);

        } catch (error) {
            console.log(error);
        }
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        setErrMsg("");
        setSuccessMsg("");
        setResponseData(null);

        if (!selectedAccount) {
            setErrMsg("Please Select Account");
            return;
        }

        try {

            const config = {
                headers: {
                    Authorization:
                        "Bearer " +
                        localStorage.getItem("token")
                }
            };

            const payload = {
                accountNumber: selectedAccount,
                amount: Number(amount),
                remarks
            };

            const response =
                await axios.post(
                    depositApi,
                    payload,
                    config
                );

            setResponseData(response.data);

            setSuccessMsg(
                "Deposit Successful"
            );

            setAmount("");
            setRemarks("");

            fetchAccounts();

        } catch (error) {

            console.log(error);

            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Deposit Failed"
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
                            Deposit Money
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

                        <form onSubmit={handleSubmit}>

                            <div className="card bg-light border-0 mb-4">

                                <div className="card-body">

                                    <h5 className="mb-3">
                                        Select Account
                                    </h5>

                                    {
                                        accounts.length > 0 ?

                                            accounts.map(account => (

                                                <div
                                                    key={account.accountId}
                                                    className={`card mb-3 border 
                                                    ${
                                                        selectedAccount === account.accountNumber
                                                            ? "border-primary border-3"
                                                            : ""
                                                    }`}
                                                    style={{
                                                        cursor:
                                                            account.accountStatus?.toUpperCase() === "ACTIVE"
                                                                ? "pointer"
                                                                : "not-allowed"
                                                    }}
                                                    onClick={() => {

                                                        if (
                                                            account.accountStatus?.toUpperCase() === "ACTIVE"
                                                        ) {
                                                            setSelectedAccount(
                                                                account.accountNumber
                                                            );
                                                        }

                                                    }}
                                                >

                                                    <div className="card-body">

                                                        <div className="d-flex align-items-start">

                                                            <input
                                                                type="radio"
                                                                className="form-check-input me-3 mt-1"
                                                                checked={
                                                                    selectedAccount ===
                                                                    account.accountNumber
                                                                }
                                                                readOnly
                                                            />

                                                            <div>

                                                                <h6 className="fw-bold">
                                                                    {
                                                                        account.accountNumber
                                                                    }
                                                                </h6>

                                                                <p className="mb-1">
                                                                    {
                                                                        account.type
                                                                    }
                                                                </p>

                                                                <p className="text-success mb-2">
                                                                    Balance :
                                                                    ₹{
                                                                        account.balance
                                                                    }
                                                                </p>

                                                                <span
                                                                    className={
                                                                        account.accountStatus?.toUpperCase() === "ACTIVE"
                                                                            ? "badge bg-success"
                                                                            : account.accountStatus?.toUpperCase() === "FROZEN"
                                                                                ? "badge bg-danger"
                                                                                : "badge bg-secondary"
                                                                    }
                                                                >
                                                                    {
                                                                        account.accountStatus
                                                                    }
                                                                </span>

                                                            </div>

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

                            <div className="mb-3">

                                <label className="form-label fw-bold">
                                    Deposit Amount
                                </label>

                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Enter Amount"
                                    value={amount}
                                    onChange={(e) =>
                                        setAmount(
                                            e.target.value
                                        )
                                    }
                                    required
                                />

                            </div>

                            <div className="mb-4">

                                <label className="form-label fw-bold">
                                    Remarks
                                </label>

                                <textarea
                                    className="form-control"
                                    rows="3"
                                    placeholder="Deposit Remarks"
                                    value={remarks}
                                    onChange={(e) =>
                                        setRemarks(
                                            e.target.value
                                        )
                                    }
                                    required
                                />

                            </div>

                            <button
                                type="submit"
                                className="btn btn-success"
                            >
                                Deposit Money
                            </button>

                        </form>

                        {
                            responseData && (

                                <div className="card border-success mt-4">

                                    <div className="card-body">

                                        <h4 className="text-success">
                                            Deposit Successful
                                        </h4>

                                        <hr />

                                        <p>
                                            <strong>
                                                Account Number :
                                            </strong>{" "}
                                            {
                                                responseData.accountNumber
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Deposited Amount :
                                            </strong>{" "}
                                            ₹{
                                                responseData.depositedAmount
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Updated Balance :
                                            </strong>{" "}
                                            ₹{
                                                responseData.updatedBalance
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Transaction Date :
                                            </strong>{" "}
                                            {
                                                responseData.transactionDate
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
    );
};

export default DepositMoney;
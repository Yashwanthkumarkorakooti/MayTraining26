import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";
import LoanDetails from "./LoanDetails";

const PayEMI = () => {

    const { loanId } = useParams();

    const [selectedAccountId, setSelectedAccountId] = useState("");
    const [amount, setAmount] = useState("");
    const [responseData, setResponseData] = useState(null);
    const [accounts, setAccounts] = useState([]);
    const [accountId, setAccountId] = useState("");

    const [errMsg, setErrMsg] = useState("");

    const payEmiApi = (id) => `http://localhost:8080/api/v1/loans/${id}/pay-emi`

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }

    useEffect(() => {
        const loadAccounts = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/v1/customer-accounts/accounts', config)
                setAccounts(response.data)
                console.log(response.data)
            } catch (error) {
                setErrMsg(error.response?.message ||
                    error.response?.data?.message ||
                    error.response?.data
                )
                console.log(error.response?.data?.message)
            }
        }
        loadAccounts()
    }, [])

    const handleSubmit = async (e) => {
        if (!selectedAccountId) {
            setErrMsg("Please select an account");
            return;
        }
        e.preventDefault();
        setErrMsg("");

        const data = {
            accountId: selectedAccountId,
            amount: Number(amount),
        }

        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }

            const response = await axios.post(payEmiApi(loanId), data, config)
            setResponseData(response.data)
            console.log(response.data)
            setAmount("")

        } catch (error) {
            console.log(error.response?.data);

            setErrMsg(
                error.response?.data?.message ||
                "EMI Payment Failed"
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
                            Pay EMI
                        </h2>

                        {
                            errMsg && (
                                <div className="alert alert-danger">
                                    {errMsg}
                                </div>
                            )
                        }

                        <form onSubmit={handleSubmit}>

                            <div className="mb-3">

                                <label className="form-label fw-bold">
                                    Loan ID
                                </label>

                                <input
                                    type="number"
                                    className="form-control"
                                    value={loanId}
                                    readOnly
                                />

                            </div>

                            <div className="mb-4">

                                <label className="form-label fw-bold">
                                    Select Account
                                </label>

                                {
                                    accounts.length > 0 ? (

                                        accounts.map(account => (

                                            <div
                                                key={account.accountId}
                                                className={`card mb-2 p-3 ${
                                                    selectedAccountId ==
                                                    account.accountId
                                                        ? "border-primary"
                                                        : ""
                                                }`}
                                            >

                                                <div className="form-check">

                                                    <input
                                                        type="radio"
                                                        className="form-check-input"
                                                        name="account"
                                                        value={
                                                            account.accountId
                                                        }
                                                        checked={
                                                            selectedAccountId ==
                                                            account.accountId
                                                        }
                                                        disabled={
                                                            account.accountStatus !==
                                                            "ACTIVE"
                                                        }
                                                        onChange={() =>
                                                            setSelectedAccountId(
                                                                account.accountId
                                                            )
                                                        }
                                                    />

                                                    <label className="form-check-label">

                                                        <strong>
                                                            {
                                                                account.accountNumber
                                                            }
                                                        </strong>

                                                        <br />

                                                        {account.type}

                                                        <br />

                                                        <span className="text-success">
                                                            Balance :
                                                            ₹{
                                                                account.balance
                                                            }
                                                        </span>

                                                        <br />

                                                        <span
                                                            className={
                                                                account.accountStatus ===
                                                                "ACTIVE"
                                                                    ? "badge bg-success"
                                                                    : "badge bg-danger"
                                                            }
                                                        >
                                                            {
                                                                account.accountStatus
                                                            }
                                                        </span>

                                                        {
                                                            account.accountStatus !==
                                                            "ACTIVE" && (
                                                                <div className="text-danger mt-1">
                                                                    Cannot be used for EMI payment
                                                                </div>
                                                            )
                                                        }

                                                    </label>

                                                </div>

                                            </div>

                                        ))

                                    ) : (

                                        <div className="alert alert-warning">
                                            No Accounts Found
                                        </div>

                                    )
                                }

                            </div>

                            <div className="mb-4">

                                <label className="form-label fw-bold">
                                    EMI Amount
                                </label>

                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Enter EMI Amount"
                                    value={amount}
                                    onChange={(e) =>
                                        setAmount(
                                            e.target.value
                                        )
                                    }
                                    required
                                />

                            </div>

                            <button className="btn btn-success submit-btn">
                                Pay EMI
                            </button>

                        </form>

                        {
                            responseData && (

                                <div className="card border-success mt-4">

                                    <div className="card-body">

                                        <h4 className="text-success">
                                            Payment Successful
                                        </h4>

                                        <p>
                                            <strong>
                                                Customer:
                                            </strong>
                                            {" "}
                                            {
                                                responseData.customerName
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                EMI Paid:
                                            </strong>
                                            {" "}
                                            ₹{
                                                responseData.emiAmount
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Remaining Balance:
                                            </strong>
                                            {" "}
                                            ₹{
                                                responseData.remainingLoanBalance
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Payment Status:
                                            </strong>
                                            {" "}
                                            {
                                                responseData.paymentStatus
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Reference:
                                            </strong>
                                            {" "}
                                            {
                                                responseData.transactionReference
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

export default PayEMI;
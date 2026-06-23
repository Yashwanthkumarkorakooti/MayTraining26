import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";

const TransferMoney = () => {

    const [accounts, setAccounts] = useState([]);
    const [beneficiaries, setBeneficiaries] = useState([]);

    const [selectedAccountId, setSelectedAccountId] = useState("");
    const [selectedBeneficiaryId, setSelectedBeneficiaryId] = useState("");

    const [amount, setAmount] = useState("");
    const [remarks, setRemarks] = useState("");

    const [responseData, setResponseData] = useState(null);
    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const accountApi =
        "http://localhost:8080/api/v1/customer-accounts/accounts";

    const beneficiaryApi =
        "http://localhost:8080/api/v1/customer/beneficiaries";

    const transferApi =
        "http://localhost:8080/api/v1/transactions/transfer";

    const config = {
        headers: {
            Authorization:
                "Bearer " +
                localStorage.getItem("token")
        }
    };

    useEffect(() => {
        fetchAccounts();
        fetchBeneficiaries();
    }, []);

    const fetchAccounts = async () => {

        try {

            const response =
                await axios.get(
                    accountApi,
                    config
                );

            setAccounts(response.data);
            

        } catch (error) {
            console.log(error);
        }
    };
    console.log(accounts);

    const fetchBeneficiaries = async () => {

        try {

            const response =
                await axios.get(
                    beneficiaryApi,
                    config
                );

            setBeneficiaries(response.data);

        } catch (error) {
            console.log(error);
        }
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        setErrMsg("");
        setSuccessMsg("");
        setResponseData(null);

        if (!selectedAccountId) {
            setErrMsg("Please select sender account");
            return;
        }

        if (!selectedBeneficiaryId) {
            setErrMsg("Please select beneficiary");
            return;
        }

        try {

            const payload = {
                fromAccountId: selectedAccountId,
                beneficiaryId: selectedBeneficiaryId,
                amount: Number(amount),
                remarks
            };

            const response =
                await axios.post(
                    transferApi,
                    payload,
                    config
                );

            setResponseData(response.data);

            setSuccessMsg(
                "Money transferred successfully"
            );

            setAmount("");
            setRemarks("");

            fetchAccounts();

        } catch (error) {

            console.log(error);

            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Transfer Failed"
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
                            Transfer Money
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

                            {/* Sender Accounts */}

                            <div className="card bg-light border-0 mb-4">

                                <div className="card-body">

                                    <h5 className="mb-3">
                                        Select Sender Account
                                    </h5>
                      

                                    {
                                        accounts.length > 0 ?

                                            accounts.map(account => {

                                                const status =
                                                    account.accountStatus ||
                                                    account.status;

                                                return (

                                                    <div
                                                        key={account.accountId}
                                                        className={`card mb-3 ${
                                                            selectedAccountId === account.accountId
                                                                ? "border-primary border-3"
                                                                : ""
                                                        }`}
                                                    >

                                                        <div className="card-body">

                                                            <div className="form-check">

                                                                <input
                                                                    type="radio"
                                                                    className="form-check-input"
                                                                    name="senderAccount"
                                                                    value={account.accountId}
                                                                    checked={
                                                                        selectedAccountId ===
                                                                        account.accountId
                                                                    }
                                                                    disabled={
                                                                        status?.toUpperCase() !==
                                                                        "ACTIVE"
                                                                    }
                                                                    onChange={() =>
                                                                        setSelectedAccountId(
                                                                            account.accountId
                                                                        )
                                                                    }
                                                                />

                                                                <label className="form-check-label ms-2">

                                                                    <h6>
                                                                        {
                                                                            account.accountNumber
                                                                        }
                                                                    </h6>

                                                                    <p className="mb-1">
                                                                        {
                                                                            account.type
                                                                        }
                                                                    </p>

                                                                    <p className="text-success mb-1">
                                                                        Balance :
                                                                        ₹{
                                                                            account.balance
                                                                        }
                                                                    </p>

                                                                    <span
                                                                        className={
                                                                            status?.toUpperCase() === "ACTIVE"
                                                                                ? "badge bg-success"
                                                                                : "badge bg-danger"
                                                                        }
                                                                    >
                                                                        {status}
                                                                    </span>

                                                                </label>

                                                            </div>

                                                        </div>

                                                    </div>

                                                );
                                            })

                                            :

                                            <div className="alert alert-warning">
                                                No Accounts Found
                                            </div>
                                    }

                                </div>

                            </div>

                            {/* Beneficiaries */}

                            <div className="card bg-light border-0 mb-4">

                                <div className="card-body">

                                    <h5 className="mb-3">
                                        Select Beneficiary
                                    </h5>

                                    {
                                        beneficiaries.length > 0 ?

                                            beneficiaries.map(
                                                beneficiary => (

                                                    <div
                                                        key={
                                                            beneficiary.beneficiaryId
                                                        }
                                                        className={`card mb-3 ${
                                                            selectedBeneficiaryId === beneficiary.beneficiaryId
                                                                ? "border-success border-3"
                                                                : ""
                                                        }`}
                                                    >

                                                        <div className="card-body">

                                                            <div className="form-check">

                                                                <input
                                                                    type="radio"
                                                                    className="form-check-input"
                                                                    name="beneficiary"
                                                                    value={
                                                                        beneficiary.beneficiaryId
                                                                    }
                                                                    checked={
                                                                        selectedBeneficiaryId ===
                                                                        beneficiary.beneficiaryId
                                                                    }
                                                                    disabled={
                                                                        beneficiary.status !==
                                                                        "ACTIVE"
                                                                    }
                                                                    onChange={() =>
                                                                        setSelectedBeneficiaryId(
                                                                            beneficiary.beneficiaryId
                                                                        )
                                                                    }
                                                                />

                                                                <label className="form-check-label ms-2">

                                                                    <h6>
                                                                        {
                                                                            beneficiary.beneficiaryName
                                                                        }
                                                                    </h6>

                                                                    <p className="mb-1">
                                                                        {
                                                                            beneficiary.accountNumber
                                                                        }
                                                                    </p>

                                                                    <p className="mb-1">
                                                                        {
                                                                            beneficiary.bankName
                                                                        }
                                                                    </p>

                                                                    <span
                                                                        className={
                                                                            beneficiary.status === "ACTIVE"
                                                                                ? "badge bg-success"
                                                                                : "badge bg-danger"
                                                                        }
                                                                    >
                                                                        {
                                                                            beneficiary.status
                                                                        }
                                                                    </span>

                                                                </label>

                                                            </div>

                                                        </div>

                                                    </div>

                                                )
                                            )

                                            :

                                            <div className="alert alert-warning">
                                                No Beneficiaries Found
                                            </div>
                                    }

                                </div>

                            </div>

                            {/* Amount */}

                            <div className="mb-3">

                                <label className="form-label fw-bold">
                                    Transfer Amount
                                </label>

                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Enter Amount"
                                    value={amount}
                                    onChange={(e) =>
                                        setAmount(e.target.value)
                                    }
                                    required
                                />

                            </div>

                            {/* Remarks */}

                            <div className="mb-4">

                                <label className="form-label fw-bold">
                                    Remarks
                                </label>

                                <textarea
                                    className="form-control"
                                    rows="3"
                                    placeholder="Transfer Remarks"
                                    value={remarks}
                                    onChange={(e) =>
                                        setRemarks(e.target.value)
                                    }
                                    required
                                />

                            </div>

                            <button
                                type="submit"
                                className="btn btn-primary submit-btn"
                            >
                                Transfer Money
                            </button>

                        </form>

                        {
                            responseData && (

                                <div className="card border-success mt-4">

                                    <div className="card-body">

                                        <h4 className="text-success">
                                            Transfer Successful
                                        </h4>

                                        <hr />

                                        <p>
                                            <strong>
                                                Amount:
                                            </strong>
                                            ₹{
                                                responseData.amount
                                            }
                                        </p>

                                        <p>
                                            <strong>
                                                Transaction Reference:
                                            </strong>
                                            {
                                                responseData.transactionReference
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

export default TransferMoney;
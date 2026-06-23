import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";

const Reports = () => {

    const [formData, setFormData] = useState({
        accountNumber: '',
        startDate: '',
        endDate: '',
    });

    const [report, setReport] = useState(null);
    const [errMsg, setErrMsg] = useState("");
    const [accounts, setAccounts] = useState([])
    const [successMsg, setSuccessMsg] = useState("");

    const statementApi = "http://localhost:8080/api/v1/reports/account-statement"
    const accountsApi = 'http://localhost:8080/api/v1/customer-accounts/accounts'

    useEffect(() => {
        loadAccounts()
    }, [])

    const loadAccounts = async () => {
        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }

            const response = await axios.get(accountsApi, config)
            setAccounts(response.data)
        } catch (error) {
            setErrMsg(error.response?.message ||
                error.response?.data?.message ||
                error.response?.data
            )
            console.log(error.response?.data?.message)
        }

    }

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]:
                e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const payload = {
            accountNumber: formData.accountNumber,
            startDate: new Date(formData.startDate).toISOString(),
            endDate: new Date(formData.endDate).toISOString(),
        };

        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }

            const response = await axios.post(statementApi, payload, config)
            setReport(response.data)
            setSuccessMsg("Statement Generated Successfully")
            console.log(response.data)

        } catch (error) {
            console.log(error.response?.data);
            setErrMsg(
                error.response?.data?.message ||
                "Failed to Generate Statement"
            )
        }
    }

    return (
        <div>

            <Navbar />
            <CustomerSidebar />

            <div className="page-content">

                <div className="card shadow border-0 form-card">

                    <div className="card-body">

                        <h2 className="mb-4">
                            Generate Account Statement
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

                        <div className="alert alert-info">
                            Generate account statement for a selected account and date range.
                        </div>

                        <form onSubmit={handleSubmit}>

                            <div className="mb-3">

                                <label className="fw-bold">
                                    Select Account
                                </label>

                                <select
                                    className="form-select"
                                    name="accountNumber"
                                    value={formData.accountNumber}
                                    onChange={handleChange}
                                    required
                                >

                                    <option value="">
                                        Select Account
                                    </option>

                                    {
                                        accounts.map(account => (

                                            <option
                                                key={account.accountId}
                                                value={
                                                    account.accountNumber
                                                }
                                            >
                                                {account.accountNumber}
                                                {" - "}
                                                {account.type}
                                                {" - ₹"}
                                                {account.balance}
                                            </option>

                                        ))
                                    }

                                </select>

                            </div>

                            <div className="mb-3">

                                <label className="fw-bold">
                                    Start Date
                                </label>

                                <input
                                    type="date"
                                    name="startDate"
                                    className="form-control"
                                    value={formData.startDate}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <div className="mb-4">

                                <label className="fw-bold">
                                    End Date
                                </label>

                                <input
                                    type="date"
                                    name="endDate"
                                    className="form-control"
                                    value={formData.endDate}
                                    onChange={handleChange}
                                    required
                                />

                            </div>

                            <button className="btn btn-primary">
                                Generate Statement
                            </button>

                        </form>

                        {
                            report && (

                                <div className="card border-success shadow-sm mt-4">

                                    <div className="card-body">

                                        <h4 className="text-success mb-3">
                                            Statement Generated
                                        </h4>

                                        <p>
                                            <strong>
                                                Customer :
                                            </strong>
                                            {" "}
                                            {report.customerName}
                                        </p>

                                        <p>
                                            <strong>
                                                Account :
                                            </strong>
                                            {" "}
                                            {report.accountNumber}
                                        </p>

                                        <p>
                                            <strong>
                                                Report Type :
                                            </strong>
                                            {" "}
                                            {report.reportType}
                                        </p>

                                        <p>
                                            <strong>
                                                Status :
                                            </strong>
                                            {" "}
                                            {report.status}
                                        </p>

                                        <hr />

                                        <p>
                                            <strong>
                                                Total Balance :
                                            </strong>
                                            {" "}
                                            ₹{report.totalBalance}
                                        </p>

                                        <p>
                                            <strong>
                                                Total Deposits :
                                            </strong>
                                            {" "}
                                            ₹{report.totalDeposits}
                                        </p>

                                        <p>
                                            <strong>
                                                Total Withdrawals :
                                            </strong>
                                            {" "}
                                            ₹{report.totalWithdrawals}
                                        </p>

                                        <p>
                                            <strong>
                                                Total Transactions :
                                            </strong>
                                            {" "}
                                            {report.totalTransactions}
                                        </p>

                                        <p>
                                            <strong>
                                                Total Loans :
                                            </strong>
                                            {" "}
                                            {report.totalLoans}
                                        </p>

                                        <p>
                                            <strong>
                                                Generated On :
                                            </strong>
                                            {" "}
                                            {new Date(
                                                report.generatedDate
                                            ).toLocaleString()}
                                        </p>

                                        <p>
                                            <strong>
                                                File :
                                            </strong>
                                            {" "}
                                            {report.filePath}
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

export default Reports;
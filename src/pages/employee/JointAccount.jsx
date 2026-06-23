import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";

const JointAccount = () => {

    const [accounts, setAccounts] = useState([]);
    const [customers, setCustomers] = useState([]);

    const [selectedAccountId, setSelectedAccountId] = useState("");
    const [selectedCustomerId, setSelectedCustomerId] = useState("");

    const [relationType, setRelationType] = useState("");
    const [accessLevel, setAccessLevel] = useState("");

    const [searchAccount, setSearchAccount] = useState("");
    const [searchCustomer, setSearchCustomer] = useState("");

    const [accountPage, setAccountPage] = useState(0);
    const [customerPage, setCustomerPage] = useState(0);

    const [accountSize] = useState(5);
    const [customerSize] = useState(5);

    const [responseData, setResponseData] = useState(null);

    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const config = {
        headers: {
            Authorization: "Bearer " + localStorage.getItem("token")}
    }

    useEffect(() => {
        fetchAccounts()
    }, [accountPage])

    useEffect(() => {
        fetchCustomers()
    }, [customerPage])

    const fetchAccounts = async () => {

        try {
            const response =
                await axios.get(
                    `http://localhost:8080/api/v1/employees/assigned-accounts?page=${accountPage}&size=${accountSize}`,
                    config
                )

            setAccounts(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error)
        }
    };

    const fetchCustomers = async () => {
        try {
            const response =
                await axios.get(
                    `http://localhost:8080/api/v1/employees/assign-customers?page=${customerPage}&size=${customerSize}`,
                    config
                );

            setCustomers(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error)
        }
    };

    const handleSubmit = async (e) => {

        e.preventDefault()

        setErrMsg("")
        setSuccessMsg("")
        setResponseData(null)

        if (!selectedAccountId) {
            setErrMsg("Please Select Account");
            return;
        }

        if (!selectedCustomerId) {
            setErrMsg("Please Select Customer");
            return;
        }

        const selectedAccount =
            accounts.find(acc => acc.accountId ===selectedAccountId)

        if (selectedAccount &&selectedAccount.accountStatus !=="ACTIVE") {
            setErrMsg("Joint holder can be added only to ACTIVE accounts")
            return;
        }

        try {

            const payload = {
                customerId:selectedCustomerId,
                relationType,
                accessLevel
            };

            const response =
                await axios.post(
                    `http://localhost:8080/api/v1/accounts/joint-holder/${selectedAccountId}`,
                    payload,
                    config
                );

            setResponseData(response.data)
            setSuccessMsg("Joint Holder Added Successfully")

            setSelectedCustomerId("")
            setRelationType("")
            setAccessLevel("")

        } catch (error) {

            console.log(error)
            setErrMsg(
                error.response?.data?.message ||
                error.response?.data ||
                "Failed To Add Joint Holder"
            )
        }
    }

    const filteredAccounts =
        accounts.filter(account =>account.customerName?.toLowerCase().includes(searchAccount.toLowerCase()) ||
            account.accountNumber ?.toLowerCase().includes( searchAccount.toLowerCase())
        )

    const filteredCustomers =
        customers.filter(customer => customer.customerName?.toLowerCase().includes(searchCustomer.toLowerCase()) ||
            customer.email?.toLowerCase().includes(searchCustomer.toLowerCase()) ||
            customer.phone?.includes(searchCustomer)
        )

    return (
        <div>

            <Navbar />
            <EmployeeSidebar />

            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body">
                        <h2 className="mb-4">
                            Add Joint Holder
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
                                    <h5>
                                        Select Account
                                    </h5>
                                    <input
                                        type="text"
                                        className="form-control mb-3"
                                        placeholder="Search Account Number / Customer"
                                        value={searchAccount}
                                        onChange={(e) =>setSearchAccount(e.target.value)}/>
                                    {
                                        filteredAccounts.length > 0 ?
                                            filteredAccounts.map(account => (
                                                <div
                                                    key={account.accountId}
                                                    className={`card mb-2 ${selectedAccountId === account.accountId
                                                        ? "border-primary border-3" : "" }`}
                                                    style={{
                                                        cursor: account.accountStatus === "ACTIVE"? "pointer": "not-allowed",
                                                        opacity: account.accountStatus === "ACTIVE" ? 1 : 0.5
                                                    }}
                                                    onClick={() => {
                                                        if (
                                                            account.accountStatus !==
                                                            "ACTIVE"
                                                        ) {
                                                            return;
                                                        }
                                                        setSelectedAccountId(
                                                            account.accountId
                                                        )

                                                    }}>

                                                    <div className="card-body">
                                                        <div className="d-flex justify-content-between">
                                                            <div>
                                                                <h6>
                                                                    {
                                                                        account.accountNumber
                                                                    }
                                                                </h6>
                                                                <p className="mb-1">
                                                                    {
                                                                        account.customerName
                                                                    }
                                                                </p>
                                                                <p className="mb-1">
                                                                    {
                                                                        account.accountType
                                                                    }
                                                                </p>

                                                            </div>
                                                            <div>
                                                                <span
                                                                    className={
                                                                        account.accountStatus === "ACTIVE"
                                                                            ? "badge bg-success": account.accountStatus === "FROZEN"
                                                                            ? "badge bg-danger": account.accountStatus === "PENDING"
                                                                            ? "badge bg-warning text-dark" : "badge bg-secondary"
                                                                    }>
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

                                    <div className="d-flex justify-content-center gap-2 mt-3">

                                        <button
                                            type="button"
                                            className="btn btn-outline-primary"
                                            disabled={accountPage === 0}
                                            onClick={() => setAccountPage(accountPage - 1)}>
                                            Previous
                                        </button>

                                        <span className="align-self-center fw-bold">
                                            Page {accountPage + 1}
                                        </span>

                                        <button
                                            type="button"
                                            className="btn btn-outline-primary"
                                            onClick={() =>setAccountPage(accountPage + 1)}>
                                            Next
                                        </button>
                                    </div>
                                </div>
                            </div>
                            

                            <div className="card bg-light border-0 mb-4">
                                <div className="card-body">
                                    <h5>
                                        Select Customer
                                    </h5>

                                    <input
                                        type="text"
                                        className="form-control mb-3"
                                        placeholder="Search Customer Name / Email / Phone"
                                        value={searchCustomer}
                                        onChange={(e) =>
                                            setSearchCustomer(
                                                e.target.value
                                            )
                                        }
                                    />

                                    {
                                        filteredCustomers.length > 0 ?
                                            filteredCustomers.map(customer => (
                                                <div
                                                    key={customer.customerId}
                                                    className={`card mb-2 ${selectedCustomerId === customer.customerId
                                                            ? "border-success border-3" : ""
                                                        }`}
                                                    style={{
                                                        cursor: "pointer"
                                                    }}
                                                    onClick={() =>
                                                        setSelectedCustomerId(
                                                            customer.customerId
                                                        )
                                                    }
                                                >
                                                    <div className="card-body">
                                                        <div className="d-flex justify-content-between">
                                                            <div>
                                                                <h6>
                                                                    {
                                                                        customer.customerName
                                                                    }
                                                                </h6>

                                                                <p className="mb-1">
                                                                    {
                                                                        customer.email
                                                                    }
                                                                </p>

                                                                <p className="mb-1">
                                                                    {
                                                                        customer.phone
                                                                    }
                                                                </p>
                                                            </div>
                                                            <div>

                                                                <span
                                                                    className={
                                                                        customer.kycStatus === "VERIFIED"
                                                                            ? "badge bg-success" : customer.kycStatus === "PENDING"
                                                                            ? "badge bg-warning text-dark" : "badge bg-danger"
                                                                    }
                                                                >
                                                                    {
                                                                        customer.kycStatus
                                                                    }
                                                                </span>
                                                                <div className="mt-2">
                                                                    <span
                                                                        className={
                                                                            customer.riskLevel === "HIGH"
                                                                                ? "badge bg-danger" : "badge bg-info"
                                                                        }
                                                                    >
                                                                        Risk :
                                                                        {
                                                                            customer.riskLevel
                                                                        }
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <hr />

                                                        <div className="row text-center">
                                                            <div className="col-md-4">
                                                                <strong>
                                                                    Accounts
                                                                </strong>
                                                                <br />
                                                                {
                                                                    customer.totalAccounts
                                                                }
                                                            </div>
                                                            <div className="col-md-4">
                                                                <strong>
                                                                    Active Loans
                                                                </strong>
                                                                <br />
                                                                {
                                                                    customer.activeLoans
                                                                }
                                                            </div>
                                                            <div className="col-md-4">
                                                                <strong>
                                                                    Balance
                                                                </strong>
                                                                <br />
                                                                ₹{
                                                                    customer.totalBalance
                                                                }

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            ))
                                            :
                                            <div className="alert alert-warning">
                                                No Customers Found
                                            </div>
                                    }
                                    <div className="d-flex justify-content-center gap-2 mt-3">
                                        <button
                                            type="button"
                                            className="btn btn-outline-success"
                                            disabled={customerPage === 0}
                                            onClick={() =>
                                                setCustomerPage(customerPage - 1)
                                            }
                                        >
                                            Previous
                                        </button>

                                        <span className="align-self-center fw-bold">
                                            Page {customerPage + 1}
                                        </span>

                                        <button
                                            type="button"
                                            className="btn btn-outline-success"
                                            onClick={() =>
                                                setCustomerPage(customerPage + 1)
                                            }
                                        >
                                            Next
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <div className="mb-3">
                                <label className="form-label">
                                    Relation Type
                                </label>

                                <select
                                    className="form-select"
                                    value={relationType}
                                    onChange={(e) =>setRelationType(e.target.value)}
                                    required
                                >
                                    <option value="">
                                        Select Relation
                                    </option>

                                    <option value="SELF">
                                        SELF
                                    </option>

                                    <option value="SPOUSE">
                                        SPOUSE
                                    </option>

                                    <option value="PARENT">
                                        PARENT
                                    </option>

                                    <option value="CHILD">
                                        CHILD
                                    </option>

                                    <option value="BUSINESS_PARTNER">
                                        BUSINESS PARTNER
                                    </option>

                                    <option value="GUARDIAN">
                                        GUARDIAN
                                    </option>

                                    <option value="OTHER">
                                        OTHER
                                    </option>
                                </select>
                            </div>

                            <div className="mb-4">
                                <label className="form-label">
                                    Access Level
                                </label>

                                <select
                                    className="form-select"
                                    value={accessLevel}
                                    onChange={(e) =>setAccessLevel(e.target.value)}
                                    required
                                >
                                    <option value="">
                                        Select Access
                                    </option>

                                    <option value="FULL_ACCESS">
                                        FULL ACCESS
                                    </option>

                                    <option value="VIEW_ONLY">
                                        VIEW ONLY
                                    </option>

                                    <option value="DEPOSIT_ONLY">
                                        DEPOSIT ONLY
                                    </option>

                                </select>
                            </div>

                            <button
                                type="submit"
                                className="btn btn-primary submit-btn"
                            >
                                Add Joint Holder
                            </button>

                        </form>
                        {
                            responseData && (

                                <div className="card border-success mt-4">
                                    <div className="card-body">
                                        <h4 className="text-success">
                                            Joint Holder Added Successfully
                                        </h4>

                                        <hr />

                                        <div className="row">
                                            <div className="col-md-6">
                                                <p>
                                                    <strong>
                                                        Account Number :
                                                    </strong>
                                                    {
                                                        responseData.accountNumber
                                                    }
                                                </p>

                                                <p>
                                                    <strong>
                                                        Customer Name :
                                                    </strong>
                                                    {
                                                        responseData.customerName
                                                    }
                                                </p>

                                                <p>
                                                    <strong>
                                                        Ownership :
                                                    </strong>
                                                    {
                                                        responseData.ownershipType
                                                    }
                                                </p>

                                            </div>

                                            <div className="col-md-6">

                                                <p>
                                                    <strong>
                                                        Relation :
                                                    </strong>
                                                    {
                                                        responseData.relationType
                                                    }
                                                </p>

                                                <p>
                                                    <strong>
                                                        Access :
                                                    </strong>
                                                    {
                                                        responseData.accessLevel
                                                    }
                                                </p>

                                                <p>
                                                    <strong>
                                                        Added By :
                                                    </strong>
                                                    {
                                                        responseData.addedEmployee
                                                    }
                                                </p>
                                            </div>
                                        </div>
                                        <div className="alert alert-success mt-3 mb-0">
                                            {
                                                responseData.message
                                            }
                                        </div>
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

export default JointAccount;
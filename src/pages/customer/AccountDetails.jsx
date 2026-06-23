import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Loader from "../../components/common/Loader";
import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";


const AccountDetails = () => {

    const navigate = useNavigate();

    const { accountId } = useParams();
    const [account, setAccount] = useState(null)
    const [loading, setLoading] = useState(true)

    const accountDetailsApi = `http://localhost:8080/api/v1/accounts/${accountId}`;

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }

    useEffect(() => {
        const fetchAccountDetails = async () => {
            try {
                const response = await axios.get(accountDetailsApi, config)
                setAccount(response.data)
                console.log(response.data)
            } catch (err) {
                // setErrMsg(error.response?.message ||
                //     error.response?.data?.message ||
                //     error.response?.data 
                // )
            console.log(err.response?.data?.message)
            } finally {
                setLoading(false)
            }
        }
        fetchAccountDetails()
    }, [])

    if (loading) return <Loader />

    return (
        <div>

            <Navbar />
            <CustomerSidebar />

            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body p-4">
                        <h2 className="mb-4"> Account Details </h2>
                        <div className="row">
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> Account Number  </label>
                                <p>  {account?.accountNumber}  </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> Account Type </label>
                                <p> {account?.accountType}
                                </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold">  Balance  </label>
                                <p> ₹ {account?.balance}  </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold">  Minimum Balance  </label>
                                <p> ₹ {account?.minimumBalance}  </p>
                            </div>

                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> Interest Rate  </label>
                                <p>{account?.interestRate} % </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> Status  </label>
                                <p>{account?.status}  </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> Branch  </label>
                                <p> {account?.branchName} </p>
                            </div>

                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> IFSC Code  </label>
                                <p> {account?.ifscCode}
                                </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold">  Ownership Type   </label>
                                <p>  {account?.ownershipType}</p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> Approved Employee  </label>
                                <p> {account?.approvedEmployee} </p>
                            </div>

                        </div>

                        {
                            account?.jointHolders?.length > 0 && (
                                <>
                                    <h4 className="mt-4"> Joint Holders </h4>
                                    <ul className="list-group">
                                        {
                                            account.jointHolders.map(
                                                (holder, index) => (
                                                    <li key={index} className="list-group-item" > {holder}</li>
                                                )
                                            )
                                        }

                                    </ul>
                                </>
                            )
                        }

                        <div className="mt-5">

                            <h4 className="mb-3">Account Actions</h4>
                            <div className="d-flex flex-wrap gap-3">
                                <button className="btn btn-success"
                                    onClick={() => navigate(`/customer/deposit?accountId=${account.accountId}`)}>
                                    Deposit Money
                                </button>

                                <button className="btn btn-warning"
                                    onClick={() => navigate(`/customer/withdraw?accountId=${account.accountId}`)}>
                                    Withdraw Money
                                </button>

                                <button className="btn btn-primary"
                                    onClick={() => navigate(`/customer/transfer?accountId=${account.accountId}`)}>
                                    Transfer Money
                                </button>

                                <button className="btn btn-danger"
                                    onClick={() => navigate(`/customer/close-account/${account.accountId}`)}>
                                    Close Account
                                </button>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    )
}

export default AccountDetails
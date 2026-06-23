import { useNavigate } from "react-router-dom"
import Navbar from "../../components/common/Navbar"
import CustomerSidebar from "../../components/customer/CustomerSidebar"
import { useEffect, useState } from "react"
import axios from "axios"
import Loader from "../../components/common/Loader"

const CustomerAccounts = () => {

    const customerId = localStorage.getItem("userId")
    console.log(customerId)

    const navigate = useNavigate();

    const [accounts,setAccounts] = useState([])
    const [loading,setLoading] = useState(true)

    const customerAccountsApi = 'http://localhost:8080/api/v1/customer-accounts/accounts';

    const config = {
        headers : {
            'Authorization' : 'Bearer ' + localStorage.getItem("token")
        }
    }

    useEffect(() => {
        const fetchAccounts = async () => {
            try{
                const response = await axios.get(customerAccountsApi,config)
                setAccounts(response.data)
                console.log(response.data)
            }catch(err){
                console.log(err)
            }finally{
                setLoading(false)
            }
        }
        fetchAccounts()
    },[])

    if(loading) return <Loader />


    return (
        <div>
            <Navbar />
            <CustomerSidebar />
            <div className="page-content">
                <div className="d-flex justify-content-between align-items-center mb-4">
                    <h2>    My Accounts  </h2>
                    <button  className="btn btn-primary"
                        onClick={() => navigate("/customer/request-account")} >
                        Request Account
                    </button>

                </div>
                <div className="row">
                    {accounts.map((account) => (
                                <div className="col-lg-4 col-md-6 mb-4" key={account.accountId} >
                                    <div className="card shadow border-0 h-100">
                                        <div className="card-body">
                                           <h5 className="fw-bold"> { account.accountType  }  </h5>
                                            <h4 className="text-primary my-3"> ₹  { account.balance} </h4>
                                            <p>  <strong>Account Number: </strong> { account.accountNumber  } </p>
                                            <p> <strong>  Branch:  </strong> { account.branchName } </p>
                                            <p> <strong>  Status: </strong> { account.accountStatus }  </p>
                                            <button
                                                className="btn btn-outline-primary w-100"
                                                onClick={() => navigate(`/customer/account-details/${account.accountId}` ) }
                                            >
                                                View Details
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            ))
                    }

                </div>

            </div>
        </div>
    )
}

export default CustomerAccounts
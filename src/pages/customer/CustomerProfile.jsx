import axios from "axios"
import { useEffect, useState } from "react"
import Loader from "../../components/common/Loader"
import Navbar from "../../components/common/Navbar"
import CustomerSidebar from "../../components/customer/CustomerSidebar"


const CustomerProfile = () => {

    const customerId = localStorage.getItem("userId")
    console.log(customerId)
    const [customer, setCustomer] = useState({})
    const [loading, setLoading] = useState(true)

    const customerProfileApi = 'http://localhost:8080/api/v1/customer-details';

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }

    useEffect(() => {
        const fetchCustomerProfile = async () => {
            try {
                const response = await axios.get(customerProfileApi, config)
                setCustomer(response.data)
                console.log(response.data)

            } catch (error) {
                console.log(error)
            } finally {
                setLoading(false)
            }
        }
        fetchCustomerProfile()
    }, [])

    if (loading) return <Loader />

    return (
        <div>
            <Navbar />
            <CustomerSidebar />
            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body p-4">
                        <h2 className="mb-4">  Customer Profile   </h2>
                        <div className="row">
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> Full Name </label>
                                <p>{ customer.fullName  } </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> Email </label>
                                <p>   {customer.email}   </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> Phone </label>
                                <p>  {customer.phone}    </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold">    Aadhaar   </label>
                                <p>  { customer.aadhaarNumber} </p>
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="fw-bold"> PAN  </label>
                                <p>  {customer.panNumber}  </p>
                            </div>

                            <div className="col-md-6 mb-3">
                                <label className="fw-bold">   KYC Status    </label>
                                <p> {customer.kycStatus}
                                </p>
                            </div>

                            <div className="col-md-12">
                                <label className="fw-bold">  Address  </label>
                                <p> {customer.address }
                                </p>
                            </div>

                        </div>

                    </div>
                </div>

            </div>
        </div>
    )

}

export default CustomerProfile
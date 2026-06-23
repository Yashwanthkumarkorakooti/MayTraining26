

import { useEffect, useState } from "react"
import Navbar from "../../components/common/Navbar"
import CustomerSidebar from "../../components/customer/CustomerSidebar"
import axios from "axios"
import Loader from "../../components/common/Loader"
import DashboardCard from "../../components/common/DashboardCard"
import CustomerAnalytics from "./CustomerAnalytics"



const CustomerDashboard = () => {

    const customerId = localStorage.getItem("userId")
    const [summary, setSummary] = useState({})
    const [loading, setLoading] = useState(true)

    const financialSummaryApi = "http://localhost:8080/api/v1/customer/financial-summary";

    const config = {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
    }

    useEffect(() => {
        const fetchSummary = async () => {
            try {
                const response = await axios.get(financialSummaryApi, config)
                setSummary(response.data)
            } catch (error) {
                console.log(error)
            } finally {
                setLoading(false)
            }
        }
        fetchSummary()
    }, [])

    if (loading) return <Loader />

    return (
        <div>
            <Navbar />
            <CustomerSidebar />
            <div className="page-content">
                <h2 className="page-title mb-4"> Customer DashBoard </h2>

                <div className="card shadow-sm border-0 mt-4">
                    <div className="card-body">
                        <h5> Welcome Back {localStorage.getItem("username")}</h5>
                        <p className="text-muted"> Manage your accounts, loans, transactions and analytics </p>
                    </div>
                </div>

                <div className="row">
                    <DashboardCard title='Total Balance' value={summary?.totalBalance}
                        icon="bi bi-wallet2" bgColor="bg-primary-card" />

                    <DashboardCard title="Monthly Spending" value={summary?.monthlySpending}
                        icon="bi bi-cash" bgColor="bg-danger-card" />

                    <DashboardCard title="Deposits" value={summary?.totalDeposits}
                        icon="bi bi-bank" bgColor="bg-success-card" />

                    <DashboardCard title="Withdrawals" value={summary?.totalWithdrawals}
                        icon="bi bi-credit-card" bgColor="bg-warning-card" />

                </div>

                <div className="mt-5">
                    <div className="card shadow border-0">
                        <div className="card-body">
                            <h3 className="mb-4">
                                Customer Analytics
                            </h3>
                            <CustomerAnalytics />
                        </div>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default CustomerDashboard
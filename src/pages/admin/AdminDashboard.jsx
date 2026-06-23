import { useEffect } from "react"
import { useDispatch, useSelector } from "react-redux"
import { Chart } from "primereact/chart"


import AdminSidebar from "../../components/admin/adminSidebar"
import Navbar from "../../components/common/Navbar"
import { getBranchPerformance, getLoanProtfolio, getRevenue, getTransactions } from "../../store/action/adminAction"


const AdminDashboard = () => {

    const dispatch = useDispatch()
    const { branchPerformance, transactionAnalytics,
        loanPortfolio, revenueAnalytics } = useSelector((state) => state.admin)

    useEffect(() => {
        dispatch(getBranchPerformance())
        dispatch(getTransactions())
        dispatch(getLoanProtfolio())
        dispatch(getRevenue())
    }, [])

    const branchChart = {
        labels: branchPerformance.map((item) => item.branchName),
        datasets: [{
            label: 'Revenue',
            data: branchPerformance.map((item) => item.revenue)
        }
        ]
    }
    // console.log(transactionAnalytics)
    const transactionChart = {
        labels: transactionAnalytics.map((item) => item.month),
        datasets: [{
            label: "Deposits",
            data: transactionAnalytics.map((item) => item.deposits),
        },
        {
            label: "Withdrawals",
            data: transactionAnalytics.map((item) => item.withdrawals),
        },
        {
            label: "Transfers",
            data: transactionAnalytics.map((item) => item.transfers),
        },
        ]
    }
    const loanChart = {
        labels:
            loanPortfolio.map((item) => item.loanType),
        datasets: [{
            data: loanPortfolio.map((item) => item.count
            )
        },
        ]
    }
    //   console.log(revenueAnalytics)
    const revenueChart = {
        labels: revenueAnalytics.map(
            (item) => item.month),
        datasets: [{
            label: "Interest Earned",
            data: revenueAnalytics.map((item) => item.interestEarned),
        },
        {
            label: "EMI Collection",
            data: revenueAnalytics.map(
                (item) => item.emiCollection
            )
        },
        ]
    }


    return (
        <div>
            <Navbar />
            <AdminSidebar />

            <div className="page-content">

                <h2 className="mb-4">
                    Admin Dashboard
                </h2>

                <div className="row">
                    <div className="col-lg-3 col-md-6 mb-4">
                        <div className="card dashboard-card bg-primary-card shadow border-0">
                            <div className="card-body">
                                <h6 className="text-light">
                                    Branches
                                </h6>
                                <h2 className="text-white">
                                    {
                                        branchPerformance.length
                                    }
                                </h2>
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-3 col-md-6 mb-4">
                        <div className="card dashboard-card bg-success-card shadow border-0">
                            <div className="card-body">
                                <h6 className="text-light">
                                    Loan Types
                                </h6>
                                <h2 className="text-white">
                                    {
                                        loanPortfolio.length
                                    }
                                </h2>
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-3 col-md-6 mb-4">
                        <div className="card dashboard-card bg-warning-card shadow border-0">
                            <div className="card-body">
                                <h6 className="text-light">
                                    Revenue Months
                                </h6>
                                <h2 className="text-white">
                                    {
                                        revenueAnalytics.length
                                    }
                                </h2>
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-3 col-md-6 mb-4">
                        <div className="card dashboard-card bg-danger-card shadow border-0">
                            <div className="card-body">
                                <h6 className="text-light">
                                    Transactions
                                </h6>
                                <h2 className="text-white">
                                    {
                                        transactionAnalytics.length
                                    }
                                </h2>
                            </div>
                        </div>
                    </div>

                </div>

                <div className="row">
                    <div className="col-lg-6 mb-4">
                        <div className="card chart-card shadow border-0">
                            <div className="card-body">
                                <h4>
                                    Branch Performance
                                </h4>
                                <Chart
                                    type="bar"
                                    data={branchChart}
                                />
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-6 mb-4">
                        <div className="card chart-card shadow border-0">
                            <div className="card-body">
                                <h4>
                                    Loan Portfolio
                                </h4>
                                <Chart
                                    type="pie"
                                    data={loanChart}
                                />
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-6 mb-4">
                        <div className="card chart-card shadow border-0">
                            <div className="card-body">
                                <h4>
                                    Revenue Analytics
                                </h4>
                                <Chart
                                    type="bar"
                                    data={revenueChart}
                                />
                            </div>
                        </div>
                    </div>

                    <div className="col-lg-6 mb-4">
                        <div className="card chart-card shadow border-0">
                            <div className="card-body">
                                <h4>
                                    Transactions
                                </h4>
                                <Chart
                                    type="bar"
                                    data={transactionChart}
                                />
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>

        </div>
    )
}

export default AdminDashboard
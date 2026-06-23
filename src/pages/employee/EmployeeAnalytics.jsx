import axios from "axios"
import { useEffect, useState } from "react"
import Loader from "../../components/common/Loader"
import Navbar from "../../components/common/Navbar"
import EmployeeSidebar from "../../components/employee/EmployeeSidebar"
import { Chart } from "primereact/chart"

const EmployeeAnalytics = () => {

    const [Loading, setLoading] = useState(true)
    const [transactionChart, setTransactionChart] = useState({})
    const [loanChart, setLoanChart] = useState({})

    const transactionApi = 'http://localhost:8080/api/v1/employees/analytics/transactions'
    const loanApi = 'http://localhost:8080/api/v1/employees/analytics/loan-status'

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }


    useEffect(() => {
        const fetchAnalytics = async () => {
            try {
                const [transactionResp, loanResp] = await Promise.all([
                    axios.get(transactionApi, config),
                    axios.get(loanApi, config),
                ])

                setTransactionChart({
                    labels: [
                        "Deposit",
                        "Withdrawal",
                        "Transfer",
                    ],
                    datasets: [{
                        label: "Transactions",
                        data: [
                            transactionResp.data.deposit,
                            transactionResp.data.withdrawal,
                            transactionResp.data.transfer,
                        ],
                    },
                    ],
                })

                setLoanChart({
                    labels:
                        loanResp.data.map(
                            (item) => item.loanStatus),

                    datasets: [{
                        data: loanResp.data.map(
                            (item) => item.count),
                    },
                    ],
                })
            } catch (err) {
                console.log(err)
            } finally {
                setLoading(false)
            }
        }
        fetchAnalytics()

    }, [])

    if (Loading) return <Loader />

    return (
        <div>

            <div className="page-content">
                <div className="row">
                    <div className="col-lg-7 mb-4">
                        <div className="card chart-card shadow border-0">
                            <div className="card-body">
                                <h4 className="mb-4"> Transaction Analytics </h4>
                                <Chart
                                    type="bar"
                                    data={transactionChart}
                                />

                            </div>
                        </div>
                    </div>

                    <div className="col-lg-5 mb-4">
                        <div className="card chart-card shadow border-0">
                            <div className="card-body">
                                <h4 className="mb-4"> Loan Status </h4>
                                <Chart
                                    type="pie"
                                    data={loanChart}
                                />

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployeeAnalytics
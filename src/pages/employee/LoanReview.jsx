import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";

const LoanReview = () => {

  const [loans,setLoans] = useState([]);

  const pendingLoanApi = "http://localhost:8080/api/v1/employees/assigned-loans";

  useEffect(() => {
    fetchPendingLoans();
  }, [])

  const fetchPendingLoans = async () => {
      try {
        const config = {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
          }
        }

        const response = await axios.get(pendingLoanApi,config)
        setLoans(response.data)
        console.log(response.data)
      } catch (error) {
        console.log(error)
      }
    }

  return (
    <div>

      <Navbar />
      <EmployeeSidebar />

      <div className="page-content">
        <div className="card shadow border-0">
          <div className="card-body">

            <h2 className="mb-4">
              Loan Reviews
            </h2>

            <div className="table-responsive">
              <table className="table table-hover">
                <thead>
                  <tr>
                    <th>
                      Loan ID
                    </th>

                    <th>
                      Customer
                    </th>

                    <th>
                      Type
                    </th>

                    <th>
                      Amount
                    </th>

                    <th>
                      Risk Level
                    </th>

                    <th>
                      Status
                    </th>

                    <th>
                      Action
                    </th>
                  </tr>

                </thead>

                <tbody>
                  {
                    loans.map((loan) => (
                        <tr key={loan.loanId}>
                          <td>
                            {
                              loan.loanId
                            }
                          </td>

                          <td>
                            {
                              loan.customerName
                            }
                          </td>

                          <td>
                            {
                              loan.loanType
                            }
                          </td>

                          <td>
                            ₹{
                              loan.loanAmount
                            }
                          </td>

                          <td>
                            {
                              loan.riskLevel
                            }
                            
                          </td>

                          <td>

                            <span className="badge bg-warning text-dark">
                              {
                                loan.loanStatus
                              }
                            </span>

                          </td>

                          <td>

                            <Link
                              to={`/employee/approve-loan/${loan.loanId}`}
                              className="btn btn-primary btn-sm me-2"
                            >
                              Review
                            </Link>

                          </td>
                        </tr>
                      )
                    )
                  }

                </tbody>

              </table>

            </div>

          </div>

        </div>

      </div>

    </div>
  )
}

export default LoanReview;
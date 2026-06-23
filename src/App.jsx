import { Navigate, Route, Routes } from 'react-router-dom'
import Login from './pages/auth/Login'
import SingUp from './pages/auth/SignUp'

import './App.css'

import AdminDashboard from './pages/admin/AdminDashboard'
import CustomerDashboard from './pages/customer/CustomerDashboard'
import ProtectedRoute from './components/common/ProtectedRoute'
import CustomerProfile from './pages/customer/CustomerProfile'
import CustomerAccounts from './pages/customer/CustomerAccounts'
import AccountDetails from './pages/customer/AccountDetails'
import RequestAccount from './pages/customer/RequestAccount'
import CloseAccount from './pages/customer/CloseAccount'
import DepositMoney from './pages/customer/DepositMoney'
import WithdrawMoney from './pages/customer/WithdrawMoney'
import TransferMoney from './pages/customer/TransferMoney'
import CustomerTransactions from './pages/customer/CustomerTransactions'
import Beneficiaries from './pages/customer/Beneficiaries'
import CustomerLoans from './pages/customer/CustomerLoans'
import ApplyLoan from './pages/customer/ApplyLoan'
import LoanDetails from './pages/customer/LoanDetails'
import PayEMI from './pages/customer/PayEMI'
import LoanEligibility from './pages/customer/LoanEligibility'
import Reports from './pages/customer/Reports'

import EmployeeDashboard from './pages/employee/EmployeeDashboard'
import EmployeeProfile from './pages/employee/EmployeeProfile'
import SearchCustomer from './pages/employee/SearchCustomer'
import AddCustomer from './pages/employee/AddCustomer'
import UpdateKyc from './pages/employee/UpdateKyc'
import AccountApproval from './pages/employee/AccountApproval'
import FreezeAccount from './pages/employee/FreezeAccount'
import JointAccount from './pages/employee/JointAccount'
import LoanReview from './pages/employee/LoanReview'
import ApproveLoan from './pages/employee/ApproveLoan'
import RejectLoan from './pages/employee/RejectLoan'
import DisburseLoan from './pages/employee/DisburseLoan'
import CreateEmployee from './pages/admin/CreateEmployee'
import Employeelist from './pages/admin/EmployeeList'
import TransferEmployee from './pages/admin/TransferEmployee'
import CreateBranch from './pages/admin/CreateBranch'
import AssignCustomer from './pages/admin/AssignCustomer'
import AssignLoan from './pages/admin/AssignLoan'
import AssignAccount from './pages/admin/AssignAccount'


function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path='/login' element={<Login />} />
      <Route path='/signup' element={<SingUp />} />

      {/* Customer  */}
      <Route path="/customer/dashboard" element={ <ProtectedRoute allowedRole="CUSTOMER"> <CustomerDashboard /> </ProtectedRoute> } />
      <Route path="/customer/profile" element={<ProtectedRoute allowedRole="CUSTOMER"> <CustomerProfile /> </ProtectedRoute> }/>
      <Route path="/customer/accounts" element={ <ProtectedRoute allowedRole="CUSTOMER"> <CustomerAccounts /> </ProtectedRoute> } />
      <Route path="/customer/account-details/:accountId" element={<ProtectedRoute allowedRole="CUSTOMER"> <AccountDetails /> </ProtectedRoute> }/>
      <Route path="/customer/request-account" element={<ProtectedRoute allowedRole="CUSTOMER"> <RequestAccount />  </ProtectedRoute>} />
      <Route path="/customer/close-account/:accountId" element={ <ProtectedRoute allowedRole="CUSTOMER"> <CloseAccount /></ProtectedRoute>} />
      <Route path="/customer/deposit" element={  <ProtectedRoute allowedRole="CUSTOMER">  <DepositMoney />  </ProtectedRoute> }/>
      <Route path="/customer/withdraw" element={ <ProtectedRoute allowedRole="CUSTOMER">  <WithdrawMoney />  </ProtectedRoute> }/>
      <Route path="/customer/transfer" element={  <ProtectedRoute allowedRole="CUSTOMER"> <TransferMoney />  </ProtectedRoute> }/>
      <Route path="/customer/transactions"  element={  <ProtectedRoute allowedRole="CUSTOMER">  <CustomerTransactions />  </ProtectedRoute>}  />  {/* pagination */}
      <Route path="/customer/beneficiaries" element={  <ProtectedRoute allowedRole="CUSTOMER"> <Beneficiaries /> </ProtectedRoute> }/>
      <Route path="/customer/apply-loan" element={ <ProtectedRoute allowedRole="CUSTOMER">  <ApplyLoan />  </ProtectedRoute>  } />
      <Route path="/customer/loans"  element={ <ProtectedRoute allowedRole="CUSTOMER">  <CustomerLoans />  </ProtectedRoute>  }  />
      <Route path="/customer/loan-details/:loanId"  element={  <ProtectedRoute allowedRole="CUSTOMER">  <LoanDetails />  </ProtectedRoute> }  />
      <Route path="/customer/pay-emi/:loanId"  element={ <ProtectedRoute allowedRole="CUSTOMER">  <PayEMI />  </ProtectedRoute>  }  />
      <Route path="/customer/loan-eligibility"  element={  <ProtectedRoute allowedRole="CUSTOMER">  <LoanEligibility />  </ProtectedRoute>  } />
      <Route path="/customer/reports" element={  <ProtectedRoute allowedRole="CUSTOMER">  <Reports />  </ProtectedRoute>  } />
    

      {/* EMPLOYEE */}
      <Route path="/employee/dashboard" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <EmployeeDashboard /> </ProtectedRoute> }/>
      <Route path="/employee/profile" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <EmployeeProfile /> </ProtectedRoute> }/>
      <Route path="/employee/add-customer" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <AddCustomer /> </ProtectedRoute> }/>
      <Route path="/employee/search-customer" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <SearchCustomer /> </ProtectedRoute> }/>
      <Route path="/employee/update-kyc/:customerId" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <UpdateKyc /> </ProtectedRoute> }/>
      <Route path="/employee/account-assign" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <AccountApproval /> </ProtectedRoute> }/>
      <Route path="/employee/freeze-account" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <FreezeAccount /> </ProtectedRoute> }/>
      <Route path="/employee/joint-account" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <JointAccount /> </ProtectedRoute> }/>
      <Route path="/employee/loan-review" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <LoanReview /> </ProtectedRoute> }/>
      <Route path="/employee/approve-loan/:loanId" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <ApproveLoan /> </ProtectedRoute> }/>
      <Route path="/employee/reject-loan/:loanId" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <RejectLoan /> </ProtectedRoute> }/>
      <Route path="/employee/disburse-loan/" element={ <ProtectedRoute allowedRole="EMPLOYEE"> <DisburseLoan /> </ProtectedRoute> }/>
      
      
     
       {/* ADMIN */}
      <Route path="/admin/dashboard" element={<ProtectedRoute allowedRole="ADMIN"> <AdminDashboard /> </ProtectedRoute> }/>
      <Route path="/admin/add-employee" element={<ProtectedRoute allowedRole="ADMIN"> <CreateEmployee /> </ProtectedRoute> }/>
      <Route path="/admin/employees" element={<ProtectedRoute allowedRole="ADMIN"> <Employeelist /> </ProtectedRoute> }/>
      <Route path="/admin/transfer-employee/:employeeId" element={<ProtectedRoute allowedRole="ADMIN"> <TransferEmployee /> </ProtectedRoute> }/>
      <Route path="/admin/create-branch" element={<ProtectedRoute allowedRole="ADMIN"> <CreateBranch /> </ProtectedRoute> }/>
      <Route path="/admin/assign-customer" element={<ProtectedRoute allowedRole="ADMIN"> <AssignCustomer /> </ProtectedRoute> }/>
      <Route path="/admin/assign-loan" element={<ProtectedRoute allowedRole="ADMIN"> <AssignLoan /> </ProtectedRoute> }/>
      <Route path="/admin/assign-account" element={<ProtectedRoute allowedRole="ADMIN"> <AssignAccount /> </ProtectedRoute> }/>


      {/* 404 */}
      <Route path="*" element={
          <div className="container mt-5 text-center">
            <h1>404 Page Not Found</h1>
          </div>
        }/>

    </Routes>
  )
}

export default App
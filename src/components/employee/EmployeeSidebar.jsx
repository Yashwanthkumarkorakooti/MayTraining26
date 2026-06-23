import { Link, useNavigate } from "react-router-dom";

const EmployeeSidebar = () => {

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <div className="sidebar">
      <div className="sidebar-header">
        <h4>
          <i className="bi bi-person-workspace me-2"></i>
          Employee
        </h4>
      </div>

      <ul className="sidebar-menu">
        <li>
          <Link to="/employee/dashboard">
            <i className="bi bi-speedometer2 me-2"></i>
            Dashboard
          </Link>
        </li>

        <li>
          <Link to="/employee/profile">
            <i className="bi bi-person me-2"></i>
            Profile
          </Link>
        </li>

        <li>
          <Link to="/employee/add-customer" >
            <i className="bi bi-person-plus me-2"></i>
            Add Customer
          </Link>
        </li>

        <li>
          <Link to="/employee/search-customer" >
            <i className="bi bi-search me-2"></i>
            Search Customer
          </Link>
        </li>

        <li>
          <Link to="/employee/loan-review" >
            <i className="bi bi-file-earmark-check me-2"></i>
            Loan Review
          </Link>
        </li>

        <li>
          <Link to="/employee/account-assign">
            <i className="bi bi-check-circle me-2"></i>
            Account Approval
          </Link>
        </li>

        <li>
          <Link to="/employee/freeze-account">
            <i className="bi bi-lock-fill me-2"></i>
            Freeze Account
          </Link>
        </li>
        <li>
          <Link to="/employee/joint-account">
            <i className="bi bi-people-fill me-2"></i>
            Joint Account
          </Link>
        </li>
        <Link to="/employee/disburse-loan">
          <i className="bi bi-cash-stack me-2"></i>
          Disburse Loan
        </Link>

        <li>
          <button className="logout-btn"
            onClick={handleLogout} >
            <i className="bi bi-box-arrow-right me-2"></i>
            Logout
          </button>
        </li>

      </ul>
    </div>
  )
}

export default EmployeeSidebar;
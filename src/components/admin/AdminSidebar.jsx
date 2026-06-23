import { Link, useNavigate } from "react-router-dom";

const AdminSidebar = () => {

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <div className="sidebar">
      <div className="sidebar-header">
        <h4>
          <i className="bi bi-shield-lock me-2"></i>
          Admin
        </h4>
      </div>

      <ul className="sidebar-menu">
        <li>
          <Link to="/admin/dashboard">
            <i className="bi bi-speedometer2 me-2"></i>
            Dashboard
          </Link>
        </li>

        <li>
          <Link to="/admin/add-employee">
            <i className="bi bi-person-plus me-2"></i>
            Add Employee
          </Link>
        </li>

        <li>
          <Link to="/admin/employees">
            <i className="bi bi-people me-2"></i>
            Employees
          </Link>
        </li>



        <li>
          <Link to="/admin/create-branch" >
            <i className="bi bi-building-add me-2"></i>
            Create Branch
          </Link>
        </li>

        <li>
          <Link to="/admin/assign-customer">
            <i className="bi bi-person-check me-2"></i>
            Assign Customer
          </Link>
        </li>

        <li>
          <Link to="/admin/assign-loan">
            <i className="bi bi-cash-coin me-2"></i>
            Assign Loan
          </Link>
        </li>

        <li>
          <Link to="/admin/assign-account">
            <i className="bi bi-person-check me-2"></i>
            Assign Account
          </Link>
        </li>


        <li>
          <button className="logout-btn"
            onClick={handleLogout}>
            <i className="bi bi-box-arrow-right me-2"></i>
            Logout
          </button>
        </li>

      </ul>
    </div>
  )
}

export default AdminSidebar;
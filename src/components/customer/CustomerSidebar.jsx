import { Link, useNavigate } from "react-router-dom";

const CustomerSidebar = () => {

    const navigate = useNavigate();
    const handleLogout = () => {
            localStorage.clear();
            navigate("/login");
        };

    return (
        <div className="sidebar">
            <div className="sidebar-header">
                <h4>
                    <i className="bi bi-person-circle me-2"></i>
                    Customer
                </h4>
            </div>
            <ul className="sidebar-menu">
                <li>
                    <Link to="/customer/dashboard" >
                        <i className="bi bi-speedometer2 me-2"></i>
                        Dashboard
                    </Link>
                </li>
                <li>
                    <Link to="/customer/profile">
                        <i className="bi bi-person me-2"></i>
                        Profile
                    </Link>
                </li>
                <li>
                    <Link to="/customer/accounts">
                        <i className="bi bi-bank me-2"></i>
                        Accounts
                    </Link>
                </li>
                <li>
                    <Link to="/customer/transactions">
                        <i className="bi bi-arrow-left-right me-2"></i>
                        Transactions
                    </Link>
                </li>
                <li>
                    <Link to="/customer/beneficiaries">
                        <i className="bi bi-people me-2"></i>
                        Beneficiaries
                    </Link>
                </li>
                <li>
                    <Link to="/customer/loans" >
                        <i className="bi bi-cash-stack me-2"></i>
                        Loans
                    </Link>
                </li>

                <li>
                    <Link to="/customer/reports" >
                        <i className="bi bi-file-earmark-text me-2"></i>
                        Reports
                    </Link>
                </li>

                {/* <li>
                    <Link to="/customer/analytics">
                        <i className="bi bi-bar-chart-line me-2"></i>
                        Analytics
                    </Link>
                </li> */}
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

export default CustomerSidebar;
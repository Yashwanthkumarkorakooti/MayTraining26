import { useNavigate } from "react-router-dom";

const Navbar = () => {

    const navigate = useNavigate();

    const username =localStorage.getItem("username" );
    const role = localStorage.getItem("role");

    const handleLogout = () => {
            localStorage.clear();
            navigate("/login");
        };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
            <div className="container-fluid">
                <span className="navbar-brand fw-bold">
                    <i className="bi bi-bank me-2"></i>
                    Bank App
                </span>
                <div className="d-flex align-items-center text-white">
                    <span className="me-3">
                        {username} {" | "}  {role}
                    </span>

                    <button
                        className="btn btn-danger btn-sm"
                        onClick={ handleLogout} >
                        Logout
                    </button>

                </div>
            </div>
        </nav>
    )
}

export default Navbar;
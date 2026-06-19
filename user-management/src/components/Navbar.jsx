import { Link } from "react-router-dom"

const Navbar = () => {
    return(
        <div>

            <nav className="navbar navbar-dark bg-dark">
                <div className="container">
                    <Link className="navbar-brand" to="/users">
                        User List
                    </Link>

                    <Link className="navbar-brand" to="/add-user">
                        Add User
                    </Link>
                </div>
            </nav>
        </div>
    )
}

export default Navbar
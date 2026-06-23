import axios from "axios";
import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"

const Login = () => {

    const [username,setUsername] = useState("")
    const [password,setPassword] = useState("")

    const[errMsg,setErrmsg] = useState('')
    const[loading,setLoading] = useState(false)

    const navigate = useNavigate();

    const loginApi = 'http://localhost:8080/api/auth/login'
    const userDetailsApi = "http://localhost:8080/api/auth/user-details";


    const onLogin = async (e) => {
        e.preventDefault()
        setErrmsg("")

        if(!username || !password){
            setErrmsg('Please fill all fileds')
            return
        }
        setLoading(true)

        const config = {
            headers: {
                'Authorization' : 'Basic ' + window.btoa(username + ":" + password)
            }
        }

        try{
            const response = await axios.get(loginApi,config)
            let token = response.data.token
            console.log(token)
            localStorage.setItem("token",token)
            localStorage.setItem("username",username)

            const configDetails = {
                headers : {
                    'Authorization' : 'Bearer ' + token
                }
            }

            const userResponse = await axios.get(userDetailsApi,configDetails)

            const role = userResponse.data.role
            const userId = userResponse.data.id 

            console.log(userResponse.data)
            console.log("ROLE:", role)
            console.log("USER ID:", userId)

            localStorage.setItem("role",role);
            localStorage.setItem("userId",userId);

            switch(role){
                case 'CUSTOMER':
                    navigate("/customer/dashboard");
                    break;
                case 'EMPLOYEE':
                    navigate("/employee/dashboard")
                    break
                case 'ADMIN':
                    navigate("/admin/dashboard")
                    break

                default:
                    setErrmsg("Unauthorized user")
                    break;
            }

        }catch(err){
            setErrmsg("Invalid Username or password")
        } finally{
            setLoading(false)
        }
    }

    return(
        <div className="login-page">
            <div className="container">
                <div className="row justify-content-center align-items-center min-vh-100">
                    <div className="col-lg-5 col-md-8 col-sm-12">
                        <div className="card login-card shadow-lg border-0">
                            <div className="card-body p-5">
                                <div className="text-center mb-4">
                                    <i className="bi bi-blank display-3 text-primary"></i>

                                    <h2 className="fw-bold mt-3"> Maverick Bank </h2>
                                    <p className="text-muted"> Login to Continue </p>
                                    {
                                        errMsg && (
                                            <div className="alert alert-danger"> {errMsg} </div>
                                        )
                                    }

                                    <form onSubmit={onLogin} >

                                        <div className="mb-3">
                                            <label className="form-label fw-semibold"> Username </label>
                                            <input type="text" className="form-control" placeholder="Enter Username" 
                                                value={username} onChange={(e)=> setUsername(e.target.value)} />
                                        </div>

                                        <div className="mb-4">
                                            <label className="form-label fw-semibold"> Password </label>
                                            <input type="password" className="form-control" placeholder="Enter Password"
                                                value={password} onChange={(e)=> setPassword(e.target.value)} />
                                        </div>

                                        <button type="submit" className="btn btn-primary w-100 py-2" disabled={loading}>
                                            {loading ? "Logging In..." : "Login"}
                                        </button>

                                    </form>

                                    <div className="text-center mt-4">
                                        <p> Don't have an account ?
                                            <Link to="/signup" className="ms-2 text-decoration-none fw-bold" >
                                                SignUp
                                            </Link>
                                             </p>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Login
import axios from "axios"
import { useState } from "react"
import {Link, useNavigate } from "react-router-dom"


const SingUp = () => {

    const navigate = useNavigate()
    const singupApi = "http://localhost:8080/api/auth/signup"

    const [formData, setFormData] =
                                    useState({
                                            username: "",
                                            password: "",
                                            email: "",
                                            phone: "",
                                    });

    const [successMsg, setSuccessMsg] = useState("")
    const [errMsg, setErrmsg] = useState("")

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name] : e.target.value
        })
    }


    const handleSingUp = async (e) => {
        e.preventDefault()
        setErrmsg("")
        setSuccessMsg("")

        try {
            await axios.post(singupApi, formData)
            setSuccessMsg("SignUp SuccessFul")
            setTimeout(() => {
                 navigate("/login")
            }, 1500)
        }
        catch (err) {
            setErrmsg("SignUp Failed")
        }
    }


    return (
        <div className="login-page">
            <div className="container">
                <div className="row justify-content-center align-items-center min-vh-100">
                    <div className="col-lg-6 col-md-8 col-sm-12">
                        <div className="card login-card shadow-lg border-0">
                            <div className="card-body p-5">

                                <div className="text-center mb-4">
                                    <i className="bi bi-person-plus-fill display-3 text-success"></i>
                                    <h2 className="fw-bold mt-3"> Create Account </h2>
                                </div>

                                {
                                    successMsg && (
                                        <div className="alert alert-success"> {successMsg} </div>
                                    )
                                }

                                {
                                    errMsg && (
                                        <div className="alert alert-danger"> {errMsg} </div>
                                    )
                                }

                                <form onSubmit={handleSingUp}>
                                    <div className="mb-3">
                                        <label className="form-label"> Username </label>
                                        <input type="text" name="username" className="form-control"
                                            value={formData.username} onChange={handleChange} />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label">Password</label>
                                        <input type="password" name="password" className="form-control"
                                            value={formData.password} onChange={handleChange} />
                                    </div>
                                    <div className="mb-3">
                                        <label className="form-label"> Email</label>
                                        <input type="email" name="email" className="form-control"
                                           value={formData.email} onChange={handleChange} />
                                    </div>

                                    <div className="mb-4">
                                        <label className="form-label"> Phone</label>
                                        <input type="text" name="phone" className="form-control"
                                           value={formData.phone} onChange={handleChange} />
                                    </div>

                                    <button className="btn btn-success w-100" >
                                        Signup
                                    </button>
                                </form>

                                <div className="text-center mt-4">
                                    Already have an account ?
                                    <Link to="/login" className="ms-2 fw-bold text-decoration-none" > Login </Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SingUp
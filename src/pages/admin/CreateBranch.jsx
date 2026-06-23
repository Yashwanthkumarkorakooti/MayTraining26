import { useState } from "react"
import AdminSidebar from "../../components/admin/adminSidebar"
import Navbar from "../../components/common/Navbar"
import axios from "axios"


const CreateBranch = () => {

    const [formData, setFormData] = useState({
        branchName: '', ifscCode: '', city: '', state: '',
        email: '', phone: '', address: '', pincode: ''
    })
    const [successMsg, setSuccessMsg] = useState('')
    const [errMsg, setErrMsg] = useState('')

    const createApi = "http://localhost:8080/api/v1/admin/branches";

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            await axios.post(createApi, formData, config)
            setSuccessMsg("Branch Creadted Successfully")
            setErrMsg('')

            setFormData({
                branchName: '', ifscCode: '', city: '', state: '',
                email: '', phone: '', address: '', pincode: ''
            })
        } catch (error) {
            setErrMsg(error.response?.message ||
                error.response?.data?.message ||
                error.response?.data ||
                "Failed to Create Branch"
            )
            console.log(error.response?.data?.message)
        }
    }


    return (
        <div>
            <Navbar />
            <AdminSidebar />

            <div className="page-content">
                <div className="card shadow border-0 form-card">
                    <div className="card-body">
                        <h2 className="mb-4">
                            Create Branch
                        </h2>
                        {
                            successMsg &&
                            (
                                <div className="alert alert-success">
                                    {
                                        successMsg
                                    }
                                </div>
                            )
                        }
                        {
                            errMsg &&
                            (
                                <div className="alert alert-danger">
                                    {
                                        errMsg
                                    }
                                </div>
                            )
                        }

                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <label>Branch Name</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="branchName"
                                        value={formData.branchName}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-md-6 mb-3">
                                    <label> IFSC Code  </label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="ifscCode"
                                        value={formData.ifscCode}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <div className="col-md-6 mb-3">
                                    <label> City </label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="city"
                                        value={formData.city}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <div className="col-md-6 mb-3">
                                    <label> State </label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="state"
                                        value={formData.state}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <div className="col-md-6 mb-3">
                                    <label>Email</label>
                                    <input
                                        type="email"
                                        className="form-control"
                                        name="email"
                                        value={formData.email}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <div className="col-md-6 mb-3">

                                    <label> Phone</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="phone"
                                        value={formData.phone}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <div className="col-12 mb-3">
                                    <label>Address </label>
                                    <textarea
                                        className="form-control"
                                        rows="4"
                                        name="address"
                                        value={formData.address}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-md-6 mb-4">
                                    <label> Pincode</label>

                                    <input
                                        type="text"
                                        className="form-control"
                                        name="pincode"
                                        value={formData.pincode}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                            </div>

                            <button className="btn btn-primary submit-btn">
                                Create Branch
                            </button>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CreateBranch
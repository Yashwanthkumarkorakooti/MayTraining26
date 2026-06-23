import { useState } from "react"
import Navbar from "../../components/common/Navbar"
import EmployeeSidebar from "../../components/employee/EmployeeSidebar"
import axios from "axios"


const AddCustomer = () => {

    const [formData, setFormData] = useState({
        username: '', email: '', phone: '',
        fullName: '', dob: '', gender: '',
        aadhaarNumber: '', panNumber: '', address: ''
    })

    const [successMsg, setSuccessMsg] = useState('')
    const [errMsg, setErrMsg] = useState('')

    const addCustomerApi = 'http://localhost:8080/api/v1/employees/customers'

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        })
    }

    const config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }


    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            const payload = {
                ...formData,
                dob: new Date(formData.dob).toISOString()
            }

            const response = await axios.post(addCustomerApi, payload, config)
            console.log(response.data)
            setSuccessMsg("Customer Added SuccessFully")

            setErrMsg('')
            setFormData({
                username: "",
                email: "",
                phone: "",
                fullName: "",
                dob: "",
                gender: "",
                aadhaarNumber: "",
                panNumber: "",
                address: "",
            })

        } catch (err) {
            console.log(err)
            setErrMsg("Failed to Add Customer")
        }
    }


    return (
        <div>
            <Navbar />
            <EmployeeSidebar />

            <div className="page-content">
                <div className="card shadow border-0 form-card">
                    <div className="card-body">
                        <h2 className="mb-4"> Add Customer </h2>
                        {
                            successMsg && (
                                <div className="alert alert-success">
                                    {successMsg}
                                </div>
                            )
                        }
                        {
                            errMsg && (
                                <div className="alert alert-danger">
                                    {errMsg}
                                </div>
                            )
                        }
                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="col-md-6 mb-3">
                                    <input type="text" name="username"
                                        placeholder="Username" className="form-control"
                                        value={formData.username}
                                        onChange={handleChange} required />
                                </div>
                                <div className="col-md-6 mb-3">
                                    <input type="email" name="email"
                                        placeholder="Email" className="form-control"
                                        value={formData.email}
                                        onChange={handleChange} required
                                    />
                                </div>

                                <div className="col-md-6 mb-3">
                                    <input type="text" name="phone"
                                        placeholder="Phone" className="form-control"
                                        value={formData.phone}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-md-6 mb-3">
                                    <input type="text" name="fullName"
                                        placeholder="Full Name" className="form-control"
                                        value={formData.fullName}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-md-6 mb-3">
                                    <input type="date" name="dob"
                                        className="form-control"
                                        value={formData.dob}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-md-6 mb-3">
                                    <select name="gender" className="form-select"
                                        value={formData.gender}
                                        onChange={handleChange} required >
                                        <option value=""> Select Gender </option>
                                        <option value="MALE"> MALE </option>
                                        <option value="FEMALE"> FEMALE </option>
                                        <option value="OTHER"> OTHER </option>
                                    </select>
                                </div>

                                <div className="col-md-6 mb-3">
                                    <input
                                        type="text" name="aadhaarNumber"
                                        placeholder="Aadhaar Number" className="form-control"
                                        value={formData.aadhaarNumber}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-md-6 mb-3">
                                    <input
                                        type="text" name="panNumber"
                                        placeholder="PAN Number" className="form-control"
                                        value={formData.panNumber}
                                        onChange={handleChange}
                                        required
                                    />
                                </div>

                                <div className="col-12 mb-3">
                                    <textarea
                                        name="address" placeholder="Address"
                                        className="form-control" rows="4"
                                        value={formData.address}
                                        onChange={handleChange}
                                        required
                                    ></textarea>
                                </div>
                            </div>
                            <button className="btn btn-primary submit-btn">
                                Add Customer
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AddCustomer
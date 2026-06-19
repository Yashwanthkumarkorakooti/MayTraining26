import axios from "axios"
import { useState } from "react"
import Navbar from "./navbar"


const AddUser = () => {

    const [addData, setAddData] = useState({})

    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [phone, setPhone] = useState('')
    const [companyName, setCompanyName] = useState('')

    const api = 'https://jsonplaceholder.typicode.com/users'

    const reqbody = {
        name: name,
        email: email,
        phone: phone,
        company: {
            name: companyName
        }
    }

    const onFormSubmit = async (e) => {
        e.preventDefault()
        try {
            const response = await axios.post(api, reqbody)
            setAddData(response.data)
            console.log(response.data)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            <Navbar />
            <div className="container mt-4">
                <h1> Add user </h1>

                <div>
                    <form onSubmit={(e) => onFormSubmit(e)}>
                        <div className="col-md-6">
                            <label> Name </label>
                            <input type="text" className="form-control"
                                value={name} onChange={(e) => setName(e.target.value)} required />
                        </div>
                        <div className="col-md-6">
                            <label> Email </label>
                            <input type="email" className="form-control"
                                value={email} onChange={(e) => setEmail(e.target.value)} required />
                        </div>
                        <div className="col-md-6">
                            <label> Phone </label>
                            <input type="text" className="form-control"
                                value={phone} onChange={(e) => setPhone(e.target.value)} required />
                        </div>
                        <div className="col-md-6">
                            <label> Company Name </label>
                            <input type="text" className="form-control"
                                value={companyName} onChange={(e) => setCompanyName(e.target.value)} required />
                        </div>

                        <div>
                            <input className="btn btn-primary mt-2" type="submit" />
                        </div>
                    </form>
                </div>

                {
                    addData && (
                        <div className="container m-2">
                            <h2> Response Data </h2>
                            <p> {addData.name} </p>
                            <p> {addData.email} </p>
                            <p> {addData.phone} </p>
                            <p> {addData.company?.name} </p>
                        </div>
                    )
                }
            </div>
        </div>
    )
}

export default AddUser
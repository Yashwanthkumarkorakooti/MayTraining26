import { useEffect, useState } from "react"
import axios from 'axios'
import Navbar from "./navbar"

const UserList = () => {

    const [usersData, setUsersData] = useState([])

    const api = 'https://jsonplaceholder.typicode.com/users'

    useEffect(() => {
        const getUserData = async () => {
            try {
                const response = await axios.get(api)
                setUsersData(response.data)
                console.log(response.data)
            }
            catch (err) {
                console.log(err)
            }
        }
        getUserData()
    }, [])

    const deleteuser = async (id) => {
        try{
            axios.delete(`{api}/${id}`)
            setUsersData(usersData.filter((user) => user.id != id))
        } catch(err){
            console.log(err)
        }
    }

    return (
        <div>
            <Navbar />
            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body">
                        <h1 className="mb-4"> Users List </h1>
                    </div>

                    <div className="table-responsive">
                        <table className="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Company</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    usersData.map((users,idx) => (
                                        <tr key={users.id}>
                                            <td> {users.name} </td>
                                            <td> {users.email} </td>
                                            <td> {users.phone} </td>
                                            <td> {users.company.name} </td>
                                            <td>
                                                <button className="btn btn-danger"
                                                onClick={()=> deleteuser(users.id)}
                                                > Delete  </button>
                                            </td>
                                        </tr>
                                    ))
                                }
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default UserList
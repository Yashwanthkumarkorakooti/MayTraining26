import { useEffect, useState } from "react";
import axios from "axios";

import Navbar from "../../components/common/Navbar";
import EmployeeSidebar from "../../components/employee/EmployeeSidebar";
import Loader from "../../components/common/Loader";

const EmployeeProfile = () => {

    const [employee, setEmployee] = useState(null);
    const [loading, setLoading] = useState(true);

    const employeeApi = 'http://localhost:8080/api/v1/employees';

    const config = {
        headers: {
            'Authorization': "Bearer " + localStorage.getItem("token")
        }
    }
    useEffect(() => {
        const fetchEmployee = async () => {
            try {
                const response = await axios.get(employeeApi, config)
                setEmployee(response.data)
            } catch (error) {
                console.log(error);
            } finally {
                setLoading(false);
            }
        }
        fetchEmployee()
    }, []);



    if (loading) {
        return <Loader />;
    }

    return (
        <div>

            <Navbar />
            <EmployeeSidebar />

            <div className="page-content">
                <div className="card shadow border-0">
                    <div className="card-body p-4">
                        <h2 className="mb-4">Employee Profile </h2>

                        <div className="row">
                            <div className="col-md-6 mb-3">
                                <strong> Employee Code  </strong>
                                <p> {employee.employeeCode} </p>
                            </div>

                            <div className="col-md-6 mb-3">
                                <strong> Full Name </strong>
                                <p> {employee.fullName} </p>
                            </div>

                            <div className="col-md-6 mb-3">
                                <strong> Email </strong>
                                <p>{employee.email}</p>

                            </div>
                            <div className="col-md-6 mb-3">
                                <strong> Phone </strong>
                                <p> {employee.phone} </p>
                            </div>

                            <div className="col-md-6 mb-3">
                                <strong> Branch </strong>
                                <p> {employee.branchName}</p>
                            </div>

                            <div className="col-md-6 mb-3">
                                <strong> Designation  </strong>
                                <p> {employee.designation} </p>
                            </div>

                            <div className="col-md-6 mb-3">
                                <strong> Employee Status </strong>
                                <p> {employee.employeeStatus} </p>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployeeProfile;
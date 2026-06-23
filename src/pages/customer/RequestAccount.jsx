import { useState } from "react";
import axios from "axios";
import Navbar from "../../components/common/Navbar";
import CustomerSidebar from "../../components/customer/CustomerSidebar";



const RequestAccount = () => {

  const customerId = localStorage.getItem("userId")

  const [type, setType] = useState("")
  const [aadhar, setAadhar] = useState(null)
  const [pan, setPan] = useState(null)
  const [photo, setPhoto] = useState(null)

  const [successMsg, setSuccessMsg] = useState("")
  const [errMsg, setErrMsg] = useState("")

  const requestAccountApi = 'http://localhost:8080/api/v1/customer/accounts/request';

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccessMsg("");
    setErrMsg("");

    const config = {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token")
      }
    }

    const formData = new FormData()
    formData.append("type", type)
    formData.append("aadhar", aadhar)
    formData.append("pan", pan)
    formData.append("photo", photo)

    try {
      const response = await axios.post(requestAccountApi, formData, config);
      console.log(response.data);
      setSuccessMsg("Account Request Submitted Successfully");
      setType("");
      setAadhar(null);
      setPan(null);
      setPhoto(null);
    } catch (error) {
      console.log(error);

      setErrMsg(
        error.response?.data?.message ||
        error.response?.data ||
        "Unable to Submit Request"
      );

    }
  };

  return (
    <div>

      <Navbar />
      <CustomerSidebar />

      <div className="page-content">
        <div className="card shadow border-0 form-card">
          <div className="card-body">
            <h2 className="form-title mb-4">
              Request New Account
            </h2>
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
              <div className="mb-4">
                <label className="form-label fw-bold">  Account Type</label>
                <select className="form-select" value={type} onChange={(e) => setType(e.target.value)} required>
                  <option value="">Select Account Type </option>
                  <option value="SAVINGS">  SAVINGS </option>
                  <option value="CURRENT">  CURRENT </option>
                  <option value="FIXED_DEPOSIT"> FIXED_DEPOSIT </option>
                </select>
              </div>
              <div className="mb-3">
                <label className="form-label fw-bold">
                  Aadhaar Card
                </label>

                <input
                  type="file"
                  className="form-control"
                  accept=".jpg,.jpeg,.png,.pdf"
                  onChange={(e) =>
                    setAadhar(e.target.files[0])
                  }
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label fw-bold">
                  PAN Card
                </label>

                <input
                  type="file"
                  className="form-control"
                  accept=".jpg,.jpeg,.png,.pdf"
                  onChange={(e) =>
                    setPan(e.target.files[0])
                  }
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label fw-bold">
                  Passport Photo
                </label>

                <input
                  type="file"
                  className="form-control"
                  accept=".jpg,.jpeg,.png"
                  onChange={(e) =>
                    setPhoto(e.target.files[0])
                  }
                  required
                />
              </div>
              <button type="submit" className="btn btn-primary submit-btn">  Request Account</button>
            </form>


          </div>


        </div>

      </div>
    </div>
  );
};

export default RequestAccount;
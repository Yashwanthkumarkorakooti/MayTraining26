const DashboardCard = ({title,value,icon,bgColor,}) => {

  return (
    <div className="col-lg-3 col-md-6 col-sm-12 mb-4">
      <div className={`card dashboard-card shadow-sm border-0 ${bgColor}`}> 
        <div className="card-body d-flex justify-content-between align-items-center">
          <div>
            <h6 className="text-light"> {title} </h6>
            <h3 className="fw-bold text-white"> {value}</h3>
          </div>

          <div>
            <i className={`${icon} dashboard-icon`} ></i>
          </div>

        </div>
      </div>

    </div>
  )
}

export default DashboardCard;
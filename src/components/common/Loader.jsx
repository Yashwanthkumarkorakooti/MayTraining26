const Loader = () => {
  return (
    <div className="loader-container">
      <div className="spinner-border text-primary" role="status" >
        <span className="visually-hidden"> Loading... </span>
      </div>

      <h5 className="mt-3">  Loading...</h5>

    </div>
  )
}

export default Loader;
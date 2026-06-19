import axios from "axios"
import { useEffect, useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import { getCharacters } from "../store/action/CharacterAction"


const Pagination = () => {

    const dispatch = useDispatch()

    const characters = useSelector(
        (state) => state.characters.characters
    )
    // const [data,setData] = useState([])
    const [page, setPage] = useState(1)

    const api = 'https://rickandmortyapi.com/api/character'

    useEffect(() => {
        // const getAll = async () => {
        //     try{
        //         const response = await axios.get(`${api}?page=${page}`)
        //         setData(response.data.results)
        //     console.log(response.data)
        //     }catch(err){
        //         console.log(err)
        //     }
        // }
        // getAll()
        dispatch(getCharacters(page))
    },[page])

    return(
        <div className="container">
            <div className="row">
                <div className=" card page-content shadow">
                    <div className="card-body">
                        <h1> Task </h1>
                    </div>

                    {characters.map((d) => (
                        <div
                            key={d.id}
                            className="border rounded p-2 mb-2"
                        >
                            <p>Name: {d.name}</p>
                            <p>Status: {d.status}</p>
                            <p>Species: {d.species}</p>
                            <p>Origin: {d.origin.name}</p>
                            <p>Location: {d.location.name}</p>
                        </div>
                    ))}

                </div>
                <div className="d-flex justify-content-center gap-2 mt-4">

                            <button className="btn btn-outline-primary"
                                disabled={page === 1}
                                onClick={() => setPage(page - 1)}>
                                Previous
                            </button>

                            <span className="align-self-center fw-bold">
                                Page {page}
                            </span>

                            <button
                                className="btn btn-outline-primary"
                                onClick={() => setPage(page + 1)}>
                                Next
                            </button>

                        </div>
            </div>
        </div>
    )
}

export default Pagination
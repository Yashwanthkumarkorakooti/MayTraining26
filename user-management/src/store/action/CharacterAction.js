// Action methods will be called from Component so make them exportable 

import axios from "axios"


const api = 'https://rickandmortyapi.com/api/character'

export const getCharacters = (page) => {
    // action fn must return a fn having action object wrapped in dispatch 
    return async (dispatch) => {
        try{
            // call the API 
            const response = await axios.get(`${api}?page=${page}`)

             // dispacth the action object
            dispatch({
                type : 'GET_CHARACTERS',
                payload : response.data.results
            })
        }
        catch(error){
            console.log(error)
        }
    }
}

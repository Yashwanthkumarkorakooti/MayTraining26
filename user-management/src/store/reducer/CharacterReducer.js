const initialState = {
    characters : []
}

// reducer Fn takes 2 arguments
// 1. state
// 2. action : this will be injected by redux using thunk


// Inject state and action in reducer and initialize state with initial value

export const CharacterReducer = (state= initialState,action) => {

    if(action.type === 'GET_CHARACTERS'){
        return{   
            ...state,                // create a clone of state and add payload to incidents
            characters : action.payload   // attach data(payload) to incidents in store
        }
    }

    return state
}

const initialState = {
    branchPerformance : [],
    transactionAnalytics : [],
    loanPortfolio : [],
    revenueAnalytics : []
}

export const adminReducer = (state=initialState,action) => {
    switch(action.type){
        case 'GET_BRANCH_PERFORMANCE':
            return {
                ...state,
                branchPerformance : action.payload
            }

        case 'GET_TRANSACTIONS' :
            return {
                ...state,
                transactionAnalytics : action.payload
            }

        case 'GET_LOAN_PORTFOLIO' :
            return {
                ...state,
                loanPortfolio : action.payload
            }

        case 'GET_REVENUE' :
            return {
                ...state,
                revenueAnalytics : action.payload
            }

        default:
            return state
    }
}
import axios from "axios";


const branchPerformanceApi = "http://localhost:8080/api/v1/admin/analytics/branch-performance";
const transactionApi = "http://localhost:8080/api/v1/admin/analytics/transactions";
const loanPortfolioApi = "http://localhost:8080/api/v1/admin/analytics/loan-portfolio";
const revenueApi = "http://localhost:8080/api/v1/admin/analytics/revenue";


const config = {
    headers : {
        'Authorization' : 'Bearer ' + localStorage.getItem('token')
    }
}

export const getBranchPerformance = () => {
    return async (dispatch) => {
        try{
            const response = await axios.get(branchPerformanceApi,config)

            dispatch({
                type : 'GET_BRANCH_PERFORMANCE',
                payload : response.data
            })
        }catch(error){
            console.log(error)
        }
    }
}

export const getTransactions = () => {
    return async (dispatch) => {
        try{
            const response = await axios.get(transactionApi,config)

            dispatch({
                type : 'GET_TRANSACTIONS',
                payload : response.data
            })
        }catch(error){
            console.log(error)
        }
    }
}

export const getLoanProtfolio = () => {
    return async (dispatch) => {
        try{
            const response = await axios.get(loanPortfolioApi,config)

            dispatch({
                type : 'GET_LOAN_PORTFOLIO',
                payload : response.data
            })
        }catch(error){
            console.log(error)
        }
    }
}

export const getRevenue = () => {
    return async (dispatch) => {
        try{
            const response = await axios.get(revenueApi,config)

            dispatch({
                type : 'GET_REVENUE',
                payload : response.data
            })
        }catch(error){
            console.log(error)
        }
    }
}


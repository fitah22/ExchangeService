import axios from "axios";

export const loginInstance = axios.create({
    //For local development
    baseURL: 'http://localhost:8080/'
});


export const tradeInstance = axios.create({
    //For local development
    baseURL: 'http://localhost:8081/'
});


export const historyInstance = axios.create({
    //For local development
    baseURL: 'http://localhost:8082/'
});
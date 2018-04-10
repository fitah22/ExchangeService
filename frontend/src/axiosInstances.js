import axios from "axios";

let authToken = undefined;

export const setAuthToken = (token) => {
    console.log("Token is updated: " + token);
    authToken = token;
};

export const loginInstance = axios.create({
    baseURL: 'http://localhost:8080/'
});

export const tradeInstance = () => {
    if (authToken === undefined) throw "AuthToken not set";
    return axios.create({
        baseURL: 'http://localhost:8081/'
    })
};

export const historyInstance = () => {
    if (authToken === undefined) throw "AuthToken not set";
    return axios.create({
        baseURL: 'http://localhost:8081/'
    })
};
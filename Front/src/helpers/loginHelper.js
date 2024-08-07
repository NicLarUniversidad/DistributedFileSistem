import { encode } from "base-64";

function getAuth() {
    const token = localStorage.getItem('logged');
    return true;//!!token;
}

function login(username, password) {
    const base64encodedData = encode(username + ":" + password);
    localStorage.setItem("token", base64encodedData);
    const url = getHealthUrl() + "myAccount";
    return fetch(url, {
        method: "GET",
        headers: {
            "Content-Type":"application/json",
            'Authorization': 'Basic ' + base64encodedData
        }
    })
}


function health(username, password) {
    const base64encodedData = encode(username + ":" + password);
    const url = getHealthUrl() + "health";
    return fetch(url, {
        method: "GET",
        crossDomain: true,
        mode: 'cors',
        withCredentials: true,
        headers: {
            "Content-Type":"application/json",
            'Authorization': 'Basic ' + base64encodedData
        }
    })
}

function logout() {
    localStorage.removeItem('logged');
}


function getHealthUrl() {
    return process.env.REACT_APP_GATEWAY_URL;
}

export {getAuth, login, logout, health, getHealthUrl}
import {getHealthUrl} from "./loginHelper";


async function getAllFiles() {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "files";
    const fetchResponse = await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            'Authorization': 'Basic ' + base64encodedData
        }
    })
    const data = await fetchResponse.json()
    console.log(data)
    return data;
}

export {getAllFiles}
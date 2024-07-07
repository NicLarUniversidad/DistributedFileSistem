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

async function uploadFile(file) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "upload-file";
    const formData = new FormData();
    formData.append("file", file, file.name);
    const fetchResponse = await fetch(url, {
        body: formData,
        method: 'POST',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Authorization': 'Basic ' + base64encodedData,
            'Access-Control-Allow-Origin': '*'
        }
    })
    const data = await fetchResponse.json()
    console.log(data)
    return data;
}

async function getFile(fileId) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "get-file/" + fileId;
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

export {getAllFiles, uploadFile, getFile}
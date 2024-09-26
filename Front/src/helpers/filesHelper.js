import {getHealthUrl} from "./loginHelper";


async function getAllFiles(page, limit) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "files?page=" + page + "&size=" + limit;
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

function splitFile(file) {
    let start = 0
    let chunkSize = Number(process.env.REACT_APP_CHUNK_SIZE)
    let chunks = []
    if (file.size < chunkSize) {
        chunks.push(file)
    }
    else {
        while (start < file.size) {
            let endChunk = start + chunkSize
            if (endChunk > file.size) {
                endChunk = file.size
            }
            const newChunk = file.slice(start, endChunk)
            chunks.push(newChunk)
            start += chunkSize
        }
    }
    return chunks
}

async function uploadPart(filePart, partNumber, isLastPart, fileName, x=0) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "upload-file";
    const formData = new FormData();
    formData.append("file", filePart, fileName);
    formData.append("x-chunk", x);
    formData.append("chunk-append", isLastPart);
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
    if (fetchResponse.status === 200) {
        let data = await fetchResponse.json();
        data["uploaded"] = true;
        return data;
    }
    else {
        return {"uploaded": false};
    }
}

async function uploadFile(file) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "upload-file";
    let chunks = splitFile(file);
    const formData = new FormData();
    formData.append("file", chunks[0], file.name);
    formData.append("x-chunk", 0);
    formData.append("chunk-append", chunks.length > 1);
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
    if (fetchResponse.status === 200) {
        let data = await fetchResponse.json()
        const x = Number(data.id);
        let correctlyUploaded = true;
        for (let i = 1; i < chunks.length; i++) {
            const formData = new FormData();
            formData.append("file", chunks[i], file.name);
            formData.append("x-chunk", x);
            formData.append("chunk-append", chunks.length > i + 1);
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
            if (fetchResponse.status === 200) {
                data = fetchResponse.json();
            }
            else {
                correctlyUploaded = false;
            }
        }
        if (correctlyUploaded) {
            return data;
        }
        else {
            await deleteFile(data.id);
            alert("Se ha producido un error al subir un chuck, se ha dado de baja el archivo.");
            return null;
        }
    }
    alert("Se ha producido un error al subir el primer chuck, no se ha subido ninguna parte.");
    return null;
}

async function refreshPart(fileId, partNumber) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "refresh-file/" + fileId + "/part/" + partNumber;
    const fetchResponse = await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            'Authorization': 'Basic ' + base64encodedData
        }
    })
    return fetchResponse.text()
}

async function getFile(fileId, refresh=false) {
    let partNumber = 1
    if (refresh) {
        await refreshPart(fileId, partNumber)
    }
    let fileData = await getFilePart(fileId, partNumber);
    let arrayBuffer = fileData.resourceString;
    console.log(fileData)
    while (fileData.hasNext) {
        partNumber += 1;
        if (refresh) {
            await refreshPart(fileId, partNumber)
        }
        fileData = await getFilePart(fileId, partNumber);
        arrayBuffer = concatenate(arrayBuffer, partNumber.resourceString);
    }
    return arrayBuffer;
}

// Concatenate a mix of typed arrays
function concatenate(...arrays) {
    // Calculate byteSize from all arrays
    let size = arrays.reduce((a,b) => a + b.byteLength, 0)
    // Allcolate a new buffer
    let result = new Uint8Array(size)

    // Build the new array
    let offset = 0
    for (let arr of arrays) {
        result.set(arr, offset)
        offset += arr.byteLength
    }

    return result
}

async function getFilePart(fileId, partNumber) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "get-file/" + fileId + "/part/" + partNumber;
    const fetchResponse = await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            'Authorization': 'Basic ' + base64encodedData
        }
    })
    return await fetchResponse.json();
}

async function deleteFile(fileId) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "delete-file/" + fileId;
    const fetchResponse = await fetch(url, {
        method: "delete",
        headers: {
            "Content-Type": "application/json",
            'Authorization': 'Basic ' + base64encodedData
        }
    })
    return await fetchResponse.arrayBuffer();
}

async function getFileParts(fileId) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "file/parts/" + fileId;
    const fetchResponse = await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            'Authorization': 'Basic ' + base64encodedData
        }
    })
    return await fetchResponse.json();
}

async function getFileLogs(fileId) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "file/log/" + fileId;
    const fetchResponse = await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            'Authorization': 'Basic ' + base64encodedData
        }
    })
    return await fetchResponse.json();
}

async function updateFile(fileId, newText) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "update-file/" + fileId;
    const formData = new FormData();
    formData.append("newText", newText);
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

async function cleanCache(fileId) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "cache/clean/" + fileId;
    const formData = new FormData();
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

async function getFileData(fileId) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "file/data/" + fileId;
    const fetchResponse = await fetch(url, {
        method: 'GET',
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

async function lockFile(fileId) {
    const base64encodedData = localStorage.getItem("token");
    const url = getHealthUrl() + "file/lock/" + fileId;
    const formData = new FormData();
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

export {getAllFiles, uploadFile, getFile, deleteFile, getFileParts, getFileLogs, updateFile, cleanCache, 
    getFileData, lockFile, splitFile, uploadPart}

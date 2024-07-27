import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {getFile, updateFile, cleanCache, getFileData, lockFile} from "../../../helpers/filesHelper";

function FormEdit(props) {

    const {id:fileId} = useParams()

    const [fileData, setFileData] = useState({})
    const [fileMetadata, setFileMetadata] = useState({"nombreArchivo" : ""})


    useEffect(() => {
        getFileData(fileId).then(data => {
            setFileMetadata(data)
            if (data.state === 'l') {
                alert("Se está editando este archivo por otro usuario, intente más tarde...")
            }
            else {
                lockFile(fileId).then(data => {
                    alert("Se bloquea archivo")
                }).catch(err => {
                        alert("No se pudo bloquear el archivo...")
                    })
            }
        })
    }, [])

    useEffect(() => {
        setFileData("Cargando...")
        getFile(fileId).then(data => {return new TextDecoder().decode(data);}).then(setFileData)
    }, [])

    const handleChange = (event) => {
        setFileData(event.target.value);
    };

    const handleUpdate = async () => {
        // We will fill this out later
        updateFile(fileId, fileData)
            .then((response) => {
                console.log(response);
                alert("Se actualizó correctamente")
                cleanCache(fileId)
                window.location.reload();
            })
            .catch((e) => {
                alert("Se ha producido un error " + e);
            });
    };

    return (
        <>
            <h2 className="w3-center">{fileMetadata.nombreArchivo}</h2>
            <textarea className="w3-input w3-border" rows="25" cols="10" id="noter-text-area" name="textarea" value={fileData} onChange={handleChange}></textarea>
            <button className="w3-btn w3-cyan" onClick={handleUpdate}>Guardar</button>
        </>
    );
}

export default FormEdit;
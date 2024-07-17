import React, {useEffect, useState} from 'react';
import {deleteFile, getAllFiles, getFile, uploadFile} from "../../helpers/filesHelper";
import FileActionPanel from "./FileActionPanel";

function FileTable(props) {


    const [files, useFiles] = useState({})
    useEffect(() => {
        getAllFiles().then(useFiles)
        console.log("Files : " + files)
    }, [])

    const handleDownloadFile = (fileId, fileName) => {
        getFile(fileId).then(response => {
            var blob = new Blob([response], {type: "application/pdf"});
            var link = document.createElement("a");
            link.href = window.URL.createObjectURL(blob);
            link.download = fileName;
            link.click();
            window.location.reload();
        })
    }

    const handleDeleteFile = (fileId) => {
        if (window.confirm('Si continua, se va a eliminar el archivo. ¿Realmente desea continuar?')) {
            deleteFile(fileId).then(response => {
                window.location.reload();
            })
                .catch(() =>{
                    alert("Ocurrió un error inesperado con el servidor")
                })
        }
    }

    return (
        <section className="w3-container">
            <h2 className="w3-auto w3-rest">Mis archivos</h2>
            <section className="w3-container w3-twothird">
                <table className="w3-table w3-bordered w3-padding-48">
                    <thead>
                        <tr className="w3-row">
                            <th>Nombre</th>
                            <th>Fecha subida</th>
                            <th>Tamaño</th>
                            <th></th>
                            {/*<th></th>*/}
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    {

                        files?.files?.map((file, idx)=> {

                            return (

                                <tr className="w3-row" key={idx}>
                                    <td>{file.nombreArchivo}</td>
                                    <td></td>
                                    {/*<td className="w3-quarter">{format(file.uploadedDate, 'yyyy/MM/dd')}</td>*/}
                                    <td>{file.tamaño}</td>
                                    <td><button onClick={() => {handleDownloadFile(file.id, file.nombreArchivo)}} className="w3-button"><i className="fas fa-download"></i></button></td>
                                    {/*<td><button className="w3-button"><i className="fas fa-edit"></i></button></td>*/}
                                    <td><button onClick={() => {handleDeleteFile(file.id)}} className="w3-button"><i className="fas fa-trash"></i></button></td>
                                    <td><a href={"/file/parts/" + file.id}  className="w3-button"><i className="fas fa-eye"></i></a></td>
                                </tr>
                            )})
                    }
                </table>
            </section>
            <FileActionPanel />
        </section>
    );
}

export default FileTable;
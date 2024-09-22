import React, {useEffect, useState} from 'react';
import {deleteFile, getAllFiles, getFile, uploadFile} from "../../helpers/filesHelper";
import FileActionPanel from "./FileActionPanel";

function FileTable(props) {


    const [files, setFiles] = useState({})
    const [limit, setLimit] = useState(5)
    const [pageNumber, setPageNumber] = useState(0)
    useEffect(() => {
        getAllFiles(0, limit).then((response) => {
            setFiles(response);
            resetButtons(response, 0);
        });
    }, [])

    const handleDownloadFile = (fileId, fileName) => {
        getFile(fileId).then(response => {
            var blob = new Blob([response], {type: "application/text;charset=utf-8"});
            var link = document.createElement("a");
            link.href = window.URL.createObjectURL(blob);
            link.download = fileName;
            link.click();
            //window.location.reload();
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

    const handleSetPageNumber = (pageNumber) => {
        setPageNumber(pageNumber);
    }

    const resetButtons = (data, page) => {
        const pageSection = window.document.querySelector("#page-buttons");
        pageSection.innerHTML = "";
        setPageNumber(page + 1)
        try {
            let start = 0;
            let finish = data.files.totalPages;
            if (page > 5) {
                start = page - 5;
            }
            if (finish > 10) {
                if (page < 5) {
                    finish = 10;
                }
                else {
                    finish = page + 5;
                }
                if (finish > data.files.totalPages) {
                    finish = data.files.totalPages;
                }
            }
            for (let i = start; i < finish; i++) {
                const newButton = window.document.createElement("button")
                newButton.textContent = (i + 1) + ""
                newButton.classList.add("w3-btn")
                newButton.classList.add("3-border")
                if (i === page) {
                    newButton.classList.add("w3-black")
                }
                else {
                    newButton.classList.add("w3-blue-grey")
                }
                newButton.classList.add("w3-text-white")
                newButton.classList.add("w3-round-xlarge")
                newButton.addEventListener("click", () => {
                    getAllFiles(i, limit).then((response) => {
                        setFiles(response);
                        resetButtons(response, i);
                    });
                });
                pageSection.appendChild(newButton);
                const space = document.createElement("span");
                space.innerHTML = "&nbsp;&nbsp;"
                pageSection.appendChild(space);
            }
        } catch (err) {
            console.log(data);
        }
    }

    return (
        <section className="w3-container">
            <h2 className="w3-auto w3-rest w3-center">Mis archivos</h2>
            <section className="w3-twothird">
                <p>Mostrando <b>{files?.files?.numberOfElements}</b> de <b>{files?.files?.totalElements}</b>
                    &nbsp;archivos</p>
                <p>Página <b>{pageNumber}</b></p>
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
                        <th></th>
                    </tr>
                    </thead>
                    {

                        files?.files?.content?.map((file, idx) => {

                            return (

                                <tr className="w3-row" key={idx}>
                                    <td>{file.nombreArchivo.substring(0, 50).padEnd(50)}</td>
                                    <td></td>
                                    {/*<td className="w3-quarter">{format(file.uploadedDate, 'yyyy/MM/dd')}</td>*/}
                                    <td>{file.tamaño}</td>
                                    <td>
                                        <button onClick={() => {
                                            handleDownloadFile(file.id, file.nombreArchivo)
                                        }} className="w3-button"><i className="fas fa-download"></i></button>
                                    </td>
                                    <td><a href={"/file-edit?id=" + file.id} className="w3-button"><i
                                        className="fas fa-edit"></i></a></td>
                                    <td><a href={"/file-logs?id=" + file.id} className="w3-button"><i
                                        className="fa-solid fa-square-poll-vertical"></i></a></td>
                                    <td><a href={"/file-parts?id=" + file.id} className="w3-button"><i
                                        className="fas fa-eye"></i></a></td>
                                    <td>
                                        <button onClick={() => {
                                            handleDeleteFile(file.id)
                                        }} className="w3-button"><i className="fas fa-trash"></i></button>
                                    </td>
                                </tr>
                            )
                        })
                    }
                </table>
                <div>&nbsp;</div>
                <div>&nbsp;</div>
                <div>&nbsp;</div>
                <section id="page-buttons" className="w3-center">
                </section>
            </section>
            <FileActionPanel/>
        </section>
    );
}

export default FileTable;
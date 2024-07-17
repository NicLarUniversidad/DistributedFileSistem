import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {getFileLogs} from "../../../helpers/filesHelper";

function LogTable(props) {

    const {id:fileId} = useParams()

    const [logs, useLogs] = useState({})

    useEffect(() => {
        getFileLogs(fileId).then(useLogs)
        console.log(logs)
    }, [])

    // const formaDate = (rawDate) => {
    //     let formattedDate = "";
    //     rawDate = rawDate.toString();
    //     formattedDate = rawDate.substring(0, 2) + "/" + rawDate.substring(3, 5) + "/" + rawDate.substring(6, 11);
    //     return formattedDate;
    // }

    const handlerCollapse = (logId) => {
        console.log("Colapse " + logId)
        let container = document.getElementById("container-" + logId);
        if (container)
            if (container.classList.contains("w3-show")) {
                container.classList.remove("w3-show")
                console.log("Quitado w3-show")
            } else {
                container.classList.add("w3-show")
                console.log("Agregado w3-show")
            }
    }

    return (
        <>
            <section className="w3-container">
                <section className="w3-container w3-center">
                    <table className="w3-table w3-bordered w3-padding-48">
                        <thead>
                        <tr className="w3-row">
                            <th>ID</th>
                            <th>Nombre</th>
                            {/*<th>Tiempo de procesamiento</th>*/}
                            {/*<th>Tiempo de inicio</th>*/}
                            {/*<th>Tiempo de finalización</th>*/}
                            <th>Tiempo de descarga</th>
                            <th>Detalles</th>
                        </tr>
                        </thead>
                        {

                            logs?.fileDownloadLog?.map((log, idx)=> {

                                return (

                                    <>
                                        <tr className="w3-row" key={idx}>
                                            <td>{log.fileLog.id}</td>
                                            <td>{log.fileLog.file}</td>
                                            <td>{log.fileLog.processTime} ms</td>
                                            {/*<td>{formaDate(log.fileLog.initTime)}</td>*/}
                                            {/*<td>{log.fileLog.finishTime}</td>*/}
                                            <td>{log.fileLog.processType}</td>
                                            <td><button onClick={() => handlerCollapse(log.fileLog.id)} className="w3-button w3-block w3-left-align">Detalles</button></td>
                                        </tr>
                                    </>
                                )})
                        }
                    </table>
                </section>
            </section>

            <h2>Detalles de descargas</h2>
            <table className="w3-table w3-bordered w3-padding-48">
                <thead>
                <tr className="w3-row">
                    <th>Nombre archivo</th>
                    <th>Nombre parte</th>
                    <th>Tiempo de descarga</th>
                    <th>Tiempo total</th>
                </tr>
                </thead>
            </table>
            {

                logs?.fileDownloadLog?.map((log, idx)=> {
                    console.log(log)

                    return (
                        <section className="w3-container w3-hide" id={"container-" + log.fileLog.id}>
                            {
                                log.partsLogs?.map((subLog, idx0) => {

                                    console.log(subLog)
                                    return (

                                        <>
                                            <div className="w3-card  w3-rest"
                                                key={idx0}>
                                                <div className="w3-quarter">{log.fileLog.file}</div>
                                                <div className="w3-quarter">{subLog.file}</div>
                                                <div className="w3-quarter">{subLog.processTime} ms</div>
                                                <div className="w3-quarter">{log.fileLog.processTime} ms</div>
                                            </div>
                                        </>
                                    )
                                })
                            }
                        </section>
                    )
                })
            }
        </>
    );
}

export default LogTable;
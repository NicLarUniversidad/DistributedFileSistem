import React, {useEffect, useState} from 'react';
import {getFileLogs, getFileParts} from "../../helpers/filesHelper";
import {useParams} from "react-router-dom";


function FilePartsTable(props) {

    const {id:fileId} = useParams()

    const [parts, useParts] = useState({})

    useEffect(() => {
        getFileParts(fileId).then(useParts)
        console.log(parts)
    }, [])


    return (
        <>
            <section className="w3-container">
                <section className="w3-container w3-center">
                    <table className="w3-table w3-bordered w3-padding-48">
                        <thead>
                        <tr className="w3-row">
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Orden</th>
                        </tr>
                        </thead>
                        {

                            parts?.parts?.map((part, idx)=> {

                                return (

                                    <tr className="w3-row" key={idx}>
                                        <td>{part.id}</td>
                                        <td>{part.nombre}</td>
                                        <td>{part.orden}</td>
                                    </tr>
                                )})
                        }
                    </table>
                </section>
            </section>
        </>
    );
}

export default FilePartsTable;
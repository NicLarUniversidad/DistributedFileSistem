import React, {useEffect, useState} from 'react';
import {getAllFiles} from "../../helpers/filesHelper";
import { format } from 'date-fns';

function FileTable(props) {


    const [files, useFiles] = useState({})
    useEffect(() => {
        getAllFiles().then(useFiles)
        console.log("Files : " + files)
    }, [])

    return (
        <section className="w3-container">
            <h2 className="w3-auto w3-rest">Mis archivos</h2>
            <section className="w3-container w3-twothird">
                <table className="w3-table w3-bordered w3-padding-48">
                    <thead>
                        <tr className="w3-row">
                            <th className="w3-quarter">Nombre</th>
                            <th className="w3-quarter">Fecha subida</th>
                            <th className="w3-quarter">Tamaño</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    {

                        files?.files?.map((file, idx)=> {

                            return (

                                <tr className="w3-row" key={idx}>
                                    <td className="w3-quarter">{file.name}</td>
                                    <td className="w3-quarter">{format(file.uploadedDate, 'yyyy/MM/dd')}</td>
                                    <td className="w3-quarter">{file.size}</td>
                                    <td><small><i className="fas fa-download"></i></small></td>
                                    <td><small><i className="fas fa-edit"></i></small></td>
                                    <td><small><i className="fas fa-trash"></i></small></td>
                                    <td><small><i className="fas fa-eye"></i></small></td>
                                </tr>
                            )})
                    }
                </table>
            </section>
            <section className="w3-container w3-third w3-grey">
                &nbsp;
            </section>
        </section>
    );
}

export default FileTable;
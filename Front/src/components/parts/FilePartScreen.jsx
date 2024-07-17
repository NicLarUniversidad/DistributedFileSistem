import React from 'react';
import {useParams} from "react-router-dom";
import Header from "../Header";
import FilePartsTable from "./FilePartsTable";
import NavBarParts from "./NavBarParts";

function FilePartScreen(props) {
    const {id:fileId} = useParams()
    return (
        <>
            <Header />

            <NavBarParts />

            <h2>Partes de archivos</h2>
            <p>Se detalla a continuación las partes en las que se dividió el archivo con id {fileId}...</p>

            <FilePartsTable />
        </>
    );
}

export default FilePartScreen;
import React from 'react';
import {useLocation} from "react-router-dom";
import Header from "../Header";
import FilePartsTable from "./FilePartsTable";
import BackArrow from "../BackArrow";

function FilePartScreen(props) {
    const search = useLocation().search;
    const fileId = new URLSearchParams(search).get("id");
    //const {id:fileId} = useParams()
    return (
        <>
            <Header />
            <BackArrow />

            <h2 className="w3-center">Partes de archivos</h2>
            <p>Se detalla a continuación las partes en las que se dividió el archivo con id {fileId}...</p>

            <FilePartsTable />
        </>
    );
}

export default FilePartScreen;
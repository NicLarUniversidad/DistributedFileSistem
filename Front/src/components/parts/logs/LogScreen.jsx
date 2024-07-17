import React from 'react';
import Header from "../../Header";
import LogTable from "./LogTable";
import {useParams} from "react-router-dom";

function LogScreen(props) {


    const {id:fileId} = useParams()

    return (
        <>
            <Header />
            <a href={"/file/parts/" + fileId}><i className="fa fa-arrow-circle-left"></i></a>
            <LogTable />
        </>
    );
}

export default LogScreen;
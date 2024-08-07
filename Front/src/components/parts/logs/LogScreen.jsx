import React from 'react';
import Header from "../../Header";
import LogTable from "./LogTable";
import {useParams} from "react-router-dom";
import BackArrow from "../../BackArrow";

function LogScreen(props) {


    const {id:fileId} = useParams()

    return (
        <>
            <Header />
            <BackArrow />
            <LogTable />
        </>
    );
}

export default LogScreen;
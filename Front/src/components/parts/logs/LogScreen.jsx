import React from 'react';
import Header from "../../Header";
import LogTable from "./LogTable";
import {useLocation} from "react-router-dom";
import BackArrow from "../../BackArrow";

function LogScreen(props) {


    const search = useLocation().search;
    const fileId = new URLSearchParams(search).get("id");
    //const {id:fileId} = useParams()

    return (
        <>
            <Header />
            <BackArrow />
            <LogTable />
        </>
    );
}

export default LogScreen;
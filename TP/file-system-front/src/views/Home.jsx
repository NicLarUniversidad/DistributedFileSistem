import React from 'react';
import Header from "../components/Header";
import FileTable from "../components/files/FileTable";

function Home(props) {

    return (
        <>
            <Header />
            <FileTable />
        </>
    );
}

export default Home;
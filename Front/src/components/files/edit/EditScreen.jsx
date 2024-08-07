import React from 'react';
import Header from "../../Header";
import FormEdit from "./FormEdit";
import BackArrow from "../../BackArrow";

function EditScreen(props) {
    return (
        <>
            <Header />
            <BackArrow />
            <FormEdit />
        </>
    );
}

export default EditScreen;
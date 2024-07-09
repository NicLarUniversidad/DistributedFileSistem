import React, {useState} from 'react';
import {uploadFile} from "../../helpers/filesHelper";

function FileActionPanel(props) {

    const [file, setFile] = useState(null);

    const handleFile = (e) => {
        e.preventDefault();
        if (e.target.files) {
            setFile(e.target.files[0]);
        }
    };

    const handleUpload = async () => {
        // We will fill this out later
        uploadFile(file)
            .then((response) => {
                console.log(response);
            })
            .catch((e) => {
                alert("Se ha producido un error " + e);
            });
    };

    return (
        <section className="w3-container w3-third w3-grey">
            &nbsp;
            <input type="file" className="w3-input" onChange={handleFile}/>
            <button className="w3-btn w3-section w3-blue-grey w3-ripple w3-col"
                onClick={handleUpload}>Subir archivo</button>
            {/* <button className="w3-btn w3-section w3-blue-grey w3-ripple w3-col">Registros</button>
            <button className="w3-btn w3-section w3-blue-grey w3-ripple w3-col">Prueba</button> */}
        </section>
    );
}

export default FileActionPanel;
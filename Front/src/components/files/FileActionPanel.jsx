import React, {useState} from 'react';
import {deleteFile, splitFile, uploadFile, uploadPart} from "../../helpers/filesHelper";

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
        // uploadFile(file)
        //     .then((response) => {
        //         console.log(response);
        //         window.location.reload();
        //     })
        //     .catch((e) => {
        //         alert("Se ha producido un error " + e);
        //     });
        const parts = splitFile(file);
        let id = 0;
        for (let i = parts.length - 1; i >= 0; i--) {
            try {
                const data = await uploadPart(parts[i], i, !i < parts.length, file.name, id);
                id = data["id"];
            }
            catch (e) {
                let retry = window.confirm("La descarga falló, se subieron " + i + " partes de " + parts.length + ", ¿reanudar la subida?");
                while (retry) {
                    try {
                        const data = await uploadPart(parts[i], i, !i < parts.length, file.name, id);
                        retry = false;
                        id = data["id"];
                    }
                    catch (e) {
                        retry = window.confirm("La descarga falló, se subieron " + i + " partes de " + parts.length + ", ¿reanudar la subida?");
                    }
                }
                if (!retry) {
                    await deleteFile(id);
                }
            }
        }
        window.location.reload();

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
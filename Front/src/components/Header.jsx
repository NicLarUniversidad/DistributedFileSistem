import React from 'react';
import {useNavigate} from "react-router-dom";
import {logout} from "../helpers/loginHelper";

function Header({username}) {

    const history = useNavigate();

    const handleLogout = (e) => {
        e.preventDefault();
        logout();
        history('/login');
    };

    return (
        <header class="w3-container w3-blue-grey w3-center">
            <h1 class="w3-twothird">Sistema de archivos distribuido</h1>
            <section class="w3-container w3-third w3-padding-24">
                <small className="w3-twothird">Usuario: {username}</small>
                <button className="w3-third" onClick={handleLogout}>Logout</button>
            </section>
        </header>
    );
}

export default Header;
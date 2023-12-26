import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {health, login} from "../helpers/loginHelper";

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const history = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        health(email, password)
            .then(() => {
                login(email, password)
                    .then(response =>{
                        localStorage.setItem("logged", "true");
                        console.log("Logeado")
                        history('/');
                        return false;
                    })
                    .catch(() => {
                        alert("Login incorrecto");
                    })
            }).catch(() => {
                alert("Login incorrecto");
            });
    };

    return (
        <div>
            <header className="w3-container w3-blue-grey w3-center">
                <h1 className="w3-twothird">Sistema de archivos distribuidos</h1>
            </header>
                <form className="w3-container w3-card-4 w3-quarter w3-display-middle" onSubmit={handleLogin}>
                    <h2>Login</h2>

                    <div>
                        <label className="w3-label w3-validate">Username:</label>
                        <input className="w3-input"
                               type="text"
                               value={email}
                               onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                    <div>
                        <label className="w3-label w3-validate">Password:</label>
                        <input className="w3-input"
                               type="password"
                               value={password}
                               onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>

                    <button type="submit" className="w3-btn w3-section w3-blue-grey w3-ripple">Login</button>
                </form>
        </div>
    );
};

export default Login;
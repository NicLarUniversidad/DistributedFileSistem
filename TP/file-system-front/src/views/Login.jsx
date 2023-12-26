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
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <div>
                    <label>Username:</label>
                    <input
                        type="text"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;
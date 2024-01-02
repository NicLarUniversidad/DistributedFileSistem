import React, { useState } from 'react';
import { Navigate  } from 'react-router-dom';

const withAuth = (Component) => {
    return (props) => {
        const [isAuthenticated, setIsAuthenticated] = useState(false);
        const [loading, setLoading] = useState(false);

        if (loading) {
            return <div>Loading...</div>;
        }

        return isAuthenticated ? <Component {...props} /> : <Navigate  to="/login" />;
    };
};

export default withAuth;
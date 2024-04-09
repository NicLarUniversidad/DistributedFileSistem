import React from 'react';
import { Route } from 'react-router-dom';
import withAuth from './withAuth';

const ProtectedRoute = ({ component: Component, ...rest }) => {
    const WrappedComponent = withAuth(Component);

    return <Route {...rest} component={WrappedComponent} />;
};

export default ProtectedRoute;
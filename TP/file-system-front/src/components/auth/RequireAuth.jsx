import {Navigate } from "react-router-dom";
import {getAuth} from "../../helpers/loginHelper";

function RequireAuth({ children, redirectTo }) {
    let isAuthenticated = getAuth();

    console.log(isAuthenticated)
    if (isAuthenticated)
        return  children
    else
        return (<>
                <Navigate  to={redirectTo} />
            </>);
}

export default RequireAuth;
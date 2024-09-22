import { BrowserRouter as Router, Routes , Route, } from 'react-router-dom';
import AdminDashboard from "../views/AdminDashboard";
import Home from "../views/Home";
import Login from "../views/Login";
import RequireAuth from "../components/auth/RequireAuth";
import FilePartScreen from "../components/parts/FilePartScreen";
import LogScreen from "../components/parts/logs/LogScreen";
import EditScreen from "../components/files/edit/EditScreen";

const AppRouter = () => {

    return (
        <>
            <Router>
                <Routes >
                    <Route path="/login" element={<Login />} exact />
                    <Route
                        path="/admin"
                        element={
                            <RequireAuth redirectTo="/login">
                                <AdminDashboard />
                            </RequireAuth>
                    }
                    />
                    <Route
                        path="/"
                        element={
                            <RequireAuth redirectTo="/login">
                                <Home />
                            </RequireAuth>}
                    />
                    <Route path={"/file-parts"} element={<FilePartScreen />} />
                    <Route path={"/file-logs"} element={<LogScreen />} />
                    <Route path={"/file-edit"} element={<EditScreen />} />
                </Routes>
            </Router>
        </>
    );
};

export default AppRouter;
import { Outlet, Navigate, useNavigate } from "react-router-dom";
import { useAuth } from "./config/authConfig";

const ProtectedRoutes = () => {

    const { user, loading } = useAuth();

    const navigate = useNavigate();

    // Waits while firebase checks if user is authenthicated
    if (loading) {
        return <h1>Loading... Please Wait!</h1>
    }

    // If the user is logged in, and allows them to access the protected routes
    // Otherwise if they're not authenthicated we redirect them to the login page 
    return user ? <Outlet />
        :
        <div className="no-login">
            <h1>You must be logged in to access that page!</h1>
            <button type="button" onClick={() => navigate("/login")}>Login</button>
        </div>
}

export default ProtectedRoutes;
import { Outlet, Navigate } from "react-router-dom";
import { useAuth } from "./config/authConfig";

const ProtectedRoutes = () => {

    const { user, loading } = useAuth();


    // Waits while firebase checks if user is authenthicated
    if (loading) {
        return <div>Loading...</div>
    }

    // If the user is logged in, and allows them to access the protected routes
    // Otherwise if they're not authenthicated we redirect them to the login page 
    return user ? <Outlet />
        :
        <Navigate to="/login" replace />;
}

export default ProtectedRoutes;
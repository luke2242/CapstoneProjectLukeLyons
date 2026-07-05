import { useNavigate } from "react-router-dom";


export default function Landing(){

      const navigate = useNavigate();

    return(
        <>

        <div className="welcome_class">
        <h1>Welcome to PulseList!</h1>
        <p>Your home for music tracking and reviewing!</p>
        </div>

        <button type="button" onClick={() => navigate("/signUp")}>Sign-Up?</button>
        
        </>
    )
}
import { Link, useNavigate } from "react-router-dom"
import { AppBar, Button, IconButton, Stack, Toolbar, Typography } from "@mui/material"
import AlbumIcon from '@mui/icons-material/Album';
import HomeIcon from '@mui/icons-material/Home';
import "../App.css";
import { useAuth } from "../config/authConfig";

export default function Navbar() {
    const navigate = useNavigate();
    const { logout } = useAuth();

const handleLogout = async () => {
  await logout();
  navigate("/login");
};

    return (
        <>
            <AppBar position="static">
                <Toolbar className="navbar">
                    <IconButton size="large" edge='start' color="inherit" aria-label="record">
                        <AlbumIcon />
                    </IconButton>
                    <Typography variant="h6" component='div' sx={{ flexGrow: 1 }}>PULSELIST</Typography>
                    <Stack direction='row' spacing={2}>
                        <Button><Link to='/home' className="navLink"><HomeIcon /></Link></Button>
                        <Button><Link to='/trending' className="navLink"> Trending </Link></Button>
                        <Button><Link to='/accountPage' className="navLink"> Your Music List </Link></Button>
                        <Button><Link to='/login' className="navLink"> Login </Link></Button>
                        <Button onClick={() => handleLogout()}>Logout</Button>
                    </Stack>
                </Toolbar>
            </AppBar>
        </>
    )
}
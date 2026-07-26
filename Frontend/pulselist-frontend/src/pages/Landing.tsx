import { Button, Paper, Stack, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import PageLayout from "../components/PageLayout";

export default function Landing() {
  const navigate = useNavigate();

  return (
    <PageLayout
      title="Welcome to PulseList"
      subtitle="Your home for music tracking, discovery, and reviews."
      maxWidth="md"
    >
      <Paper elevation={6} sx={{ p: { xs: 3, md: 4 } }}>
        <Stack spacing={2}>
          <Typography>
            Build a listening queue, follow trends, and keep your music life organized.
          </Typography>
          <Stack direction={{ xs: "column", sm: "row" }} spacing={1.5}>
            <Button variant="contained" onClick={() => navigate("/signup")}>
              Create Account
            </Button>
            <Button variant="outlined" onClick={() => navigate("/login")}>
              Login
            </Button>
          </Stack>
        </Stack>
      </Paper>
    </PageLayout>
  );
}

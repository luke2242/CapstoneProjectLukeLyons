import { Button, Paper, Stack, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import PageLayout from "../components/PageLayout";

export default function Home() {
  const navigate = useNavigate();

  return (
    <PageLayout
      showNavbar
      title="PulseList"
      subtitle="Track what your music, discover new releases and enhance your listening journey."
      maxWidth="md"
    >
      <Paper elevation={6} sx={{ p: { xs: 3, md: 4 } }}>
        <Stack spacing={3}>
          <Typography variant="h5">Welcome Home</Typography>
          <Typography sx={{ color: "text.secondary" }}>
            Dive into trending releases, search releases, and keep your personal library up to date.
          </Typography>

          <Stack direction={{ xs: "column", sm: "row" }} spacing={1.5}>
            <Button variant="contained" onClick={() => navigate("/search")}>
              Start Searching
            </Button>
            <Button variant="outlined" onClick={() => navigate("/trending")}>
              Explore Trending
            </Button>
            <Button variant="outlined" onClick={() => navigate("/account")}>
              Open Your List
            </Button>
          </Stack>
        </Stack>
      </Paper>
    </PageLayout>
  );
}
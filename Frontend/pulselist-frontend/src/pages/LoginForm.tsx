import { useState } from "react";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import { signInWithEmailAndPassword } from "firebase/auth";
import { Alert, Box, Button, Container, Link, Paper, Stack, TextField, Typography } from "@mui/material";
import { useAuth } from "../config/authConfig";
import { auth } from "../firebaseConfig";
import { signInBackend } from "../api/listManagementApi";

export default function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { login: loginToAuth } = useAuth();
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setIsSubmitting(true);

    try {
      await loginToAuth(email, password);

      const userCredential = await signInWithEmailAndPassword(auth, email, password);
      const idToken = await userCredential.user.getIdToken();

      await signInBackend(idToken);
      navigate("/account");
    } catch (err: any) {
      setError(err?.message ?? "Login failed");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Box
      sx={() => ({
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        py: 6
      })}
    >
      <Container maxWidth="sm">
        <Paper elevation={8} sx={{ p: { xs: 3, sm: 5 }, color: "text.primary" }}>
          <Stack spacing={3}>
            <Stack spacing={1.5} sx={{ alignItems: "center", textAlign: "center" }}>

              <Typography
                variant="h4"
                component="h1"
                sx={{
                  fontWeight: 800,
                  letterSpacing: "0.2rem",
                }}
              >
                PULSELIST
              </Typography>

              <Typography sx={{ color: "text.secondary" }}>
                Welcome back. Pick up your music journey.
              </Typography>
            </Stack>

            <Box component="form" onSubmit={handleSubmit} noValidate>
              <Stack spacing={2}>
                <TextField
                  type="email"
                  label="Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  fullWidth
                  autoComplete="email"
                />

                <TextField
                  type="password"
                  label="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                  fullWidth
                  autoComplete="current-password"
                />

                {error ? (
                  <Alert severity="error" sx={{ borderRadius: 2 }}>
                    {error}
                  </Alert>
                ) : null}

                <Button type="submit" variant="contained" color="primary" size="large" disabled={isSubmitting}>
                  {isSubmitting ? "Logging In..." : "Login"}
                </Button>
              </Stack>
            </Box>

            <Typography sx={{ textAlign: "center", color: "text.secondary" }}>
              New to PulseList?{" "}
              <Link component={RouterLink} to="/signUp">
                Sign up here
              </Link>
            </Typography>
          </Stack>
        </Paper>
      </Container>
    </Box>
  );
}

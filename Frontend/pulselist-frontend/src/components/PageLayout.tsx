import type { ReactNode } from "react";
import { Box, Container, Typography } from "@mui/material";
import Navbar from "./Navbar";

type PageLayoutProps = {
  children: ReactNode;
  title?: string;
  subtitle?: string;
  showNavbar?: boolean;
  maxWidth?: "sm" | "md" | "lg" | "xl";
};

export default function PageLayout({
  children,
  title,
  subtitle,
  showNavbar = false,
  maxWidth = "lg",
}: PageLayoutProps) {
  return (
    <Box
      sx={{
        minHeight: "100vh",
      }}
    >
      {showNavbar ? <Navbar /> : null}

      <Container maxWidth={maxWidth} sx={{ py: { xs: 3, md: 5 } }}>
        {title ? (
          <Box sx={{ mb: 3, textAlign: { xs: "left", md: "center" } }}>
            <Typography variant="h3" component="h1" sx={{ mb: 1 }}>
              {title}
            </Typography>
            {subtitle ? (
              <Typography sx={{ color: "text.secondary" }}>{subtitle}</Typography>
            ) : null}
          </Box>
        ) : null}

        {children}
      </Container>
    </Box>
  );
}

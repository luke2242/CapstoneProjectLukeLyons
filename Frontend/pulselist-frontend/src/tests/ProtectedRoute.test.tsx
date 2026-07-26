import { describe, test, expect, vi, beforeEach } from "vitest";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
import { MemoryRouter, Routes, Route } from "react-router-dom";
import type { User } from "firebase/auth";

import ProtectedRoutes from "../ProtectedRoutes";
import { useAuth } from "../config/authConfig";

vi.mock("../config/authConfig", () => ({
  useAuth: vi.fn(),
}));

describe("ProtectedRoutes", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  test("Renders protected content when user is authenticated", () => {
    const mockUser = {
      uid: "123",
    } as User;

    vi.mocked(useAuth).mockReturnValue({
      user: mockUser,
      token: "fake-token",
      loading: false,
      login: vi.fn(),
      logout: vi.fn(),
    });

    render(
      <MemoryRouter initialEntries={["/account"]}>
        <Routes>
          <Route element={<ProtectedRoutes />}>
            <Route path="/account" element={<h1>Account Page</h1>} />
          </Route>

          <Route path="/login" element={<h1>Login Page</h1>} />
        </Routes>
      </MemoryRouter>
    );

    expect(screen.getByText("Account Page")).toBeInTheDocument();
  });

  test("Redirects to login when user is not authenticated", () => {
    vi.mocked(useAuth).mockReturnValue({
      user: null,
      token: null,
      loading: false,
      login: vi.fn(),
      logout: vi.fn(),
    });

    render(
      <MemoryRouter initialEntries={["/account"]}>
        <Routes>
          <Route element={<ProtectedRoutes />}>
            <Route path="/account" element={<h1>Account Page</h1>} />
          </Route>

          <Route path="/login" element={<h1>Login Page</h1>} />
        </Routes>
      </MemoryRouter>
    );

    expect(screen.getByText("Login Page")).toBeInTheDocument();
  });

  test("Shows loading while authentication is being checked", () => {
    vi.mocked(useAuth).mockReturnValue({
      user: null,
      token: null,
      loading: true,
      login: vi.fn(),
      logout: vi.fn(),
    });

    render(
      <MemoryRouter>
        <ProtectedRoutes />
      </MemoryRouter>
    );

    expect(screen.getByText("Loading your session...")).toBeInTheDocument();
  });
});

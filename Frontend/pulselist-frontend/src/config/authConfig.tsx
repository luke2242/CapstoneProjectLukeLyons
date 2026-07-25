import { createContext, useContext, useEffect, useMemo, useState, type ReactNode } from "react";
import { getAuth, signInWithEmailAndPassword, signOut, onAuthStateChanged, type User } from "firebase/auth";
import { app } from "../firebaseConfig";

const auth = getAuth(app);

type AuthContextType = {
    user: User | null;
    token: string | null;
    loading: boolean;
    login: (email: string, password: string ) => Promise<void>
    logout: () => Promise<void>
}


const AuthContext = createContext<AuthContextType | null>(null);

let currentToken: string | null = null;

export function setAccessToken(token: string | null){
    currentToken = token;
}

export function getAccessToken(){
    return currentToken;
}


export async function api<T>(path: string, init: RequestInit = {}): Promise<T> {
  const headers = new Headers(init.headers);
  if (currentToken) {
    headers.set("Authorization", `Bearer ${currentToken}`);
  }

  const baseUrl = import.meta.env.VITE_API_URL;
  const res = await fetch(`${baseUrl}${path}`, {
    ...init,
    headers,
  });

  if (!res.ok) {
    throw new Error(await res.text());
  }

  return res.json() as Promise<T>;
}

export const authConfig = {
  loginPath: '/login',
  signupPath: '/signup',
  afterSignInPath: '/account',
  afterSignUpPath: '/account',
} as const;

export const getSafeAuthRedirect = (path?: string) => {
  if (!path || path === 'undefined' || path === 'null') {
    return authConfig.afterSignUpPath;
  }
  return path;
};

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, async (firebaseUser) => {
      setUser(firebaseUser);

      if (firebaseUser) {
        const idToken = await firebaseUser.getIdToken();
        setToken(idToken);
        setAccessToken(idToken);
      } else {
        setToken(null);
        setAccessToken(null);
      }

      setLoading(false);
    });
    return unsubscribe;
  }, []);

  const login = async (email: string, password: string) => {
    const result = await signInWithEmailAndPassword(auth, email, password);
    const idToken = await result.user.getIdToken();
    setToken(idToken);
    setAccessToken(idToken);
  };

  const logout = async () => {
    await signOut(auth);
    setToken(null);
    setAccessToken(null);
  };

  const value = useMemo(
    () => ({ user, token, loading, login, logout }),
    [user, token, loading]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used inside AuthProvider");
  return ctx;
}
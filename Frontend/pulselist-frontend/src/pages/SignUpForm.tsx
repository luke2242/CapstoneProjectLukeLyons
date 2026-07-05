import { useState } from "react";
import { createUserWithEmailAndPassword, getIdToken, updateProfile } from "firebase/auth";
import { auth } from "../firebaseConfig";
import { useMutation } from "@tanstack/react-query";
import axios from "axios";

function SignUpForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [username, setUsername] = useState("");

  const { mutate, isPending, isError, error } = useMutation({
    mutationFn: async () => {
      const credential = await createUserWithEmailAndPassword(auth, email, password);

      await updateProfile(credential.user, {
        displayName: username,
      });

      const token = await getIdToken(credential.user);

      const res = await axios.post(
        import.meta.env.VITE_PULSELIST_ADDUSERURL,
        {
          username: username,
          email: credential.user.email,
          uid: credential.user.uid,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      return res.data;
    },
    onSuccess: (data) => {
      console.log("User profile synced with springboot DB:", data);
      alert("Registered!");
    },
    onError: (err) => {
      console.error("An error occurred during registration", err);
    },
  });

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    mutate();
  };

  return (
    <div className="login-form">
      <h1>Hello World!</h1>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <input
          type="password"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit" disabled={isPending}>
          {isPending ? "Registering user... Please wait!" : "Sign Up!"}
        </button>

        {isError && (
          <p style={{ color: "red" }}>
            Error: {(error as any)?.response?.data?.message || error.message}
          </p>
        )}
      </form>
    </div>
  );
}

export default SignUpForm;
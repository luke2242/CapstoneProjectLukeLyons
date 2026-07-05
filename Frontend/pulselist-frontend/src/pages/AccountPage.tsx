import { useAuth } from "../config/authConfig";

export default function AccountPage() {
  const { user } = useAuth();

  return (
    <div>
      <h1>Welcome {user?.displayName || "User"}!</h1>
    </div>
  );
}
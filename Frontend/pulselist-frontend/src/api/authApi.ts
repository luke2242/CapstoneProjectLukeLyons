import { getAuth } from "firebase/auth";

// This fetch ensures our user is logged in before fetching discogs
export async function authFetch(
  url: string,
  options: RequestInit = {}
) {
  const auth = getAuth();
  const user = auth.currentUser;

  if (!user) {
    throw new Error("User is not logged in");
  }

  const token = await user.getIdToken();

  return fetch(url, {
    ...options,
    headers: {
      ...options.headers,
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
}
const API_URL = `${import.meta.env.VITE_API_URL}/api`;

import { getAuth } from "firebase/auth";

export const addToList = async (release: any) => {
  const auth = getAuth();
  const user = auth.currentUser;

  if (!user) {
    throw new Error("User is not logged in");
  }

  const token = await user.getIdToken();

  await fetch(`${API_URL}/user-music-list`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
    body: JSON.stringify({
      discogsReleaseId: release.id,
      discogsTitle: release.title,
      discogsArtist: release.artists_sort || "Unknown artist",
      discogsCoverUrl: release.cover_image,
      status: "WANT_TO_LISTEN",
    }),
  });
};

export const changeStatus = async (entryId: number, status: string) => {
  await fetch(`${API_URL}/user-music-list/${entryId}/status`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ status })
  });
};

export const removeFromList = async (entryId: number) => {
  await fetch(`${API_URL}/user-music-list/${entryId}`, {
    method: "DELETE"
  });
};
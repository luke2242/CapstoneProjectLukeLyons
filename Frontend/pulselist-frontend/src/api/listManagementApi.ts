import { authFetch } from "./authApi";
import type { ListeningStatus } from "../types/listStatus";

export type FirebaseSignInRes = unknown;

// Mirrors our backend user music list entry
export type UserMusicListEntry = {
  id: number;
  userId: number;
  discogsReleaseId: number;
  discogsTitle: string;
  discogsArtist: string;
  discogsCoverUrl?: string;
  status: ListeningStatus;
};

export type AddToMusicListPayload = {
  discogsReleaseId: number;
  discogsTitle: string;
  discogsArtist: string;
  discogsCoverUrl?: string;
  status: ListeningStatus;
};

export const addToList = async (
  payload: AddToMusicListPayload
): Promise<UserMusicListEntry> => {
  const response = await authFetch(
    `${import.meta.env.VITE_API_URL}/api/user-music-list`,
    {
      method: "POST",
      body: JSON.stringify(payload),
    }
  );

  if (!response.ok) {
    throw new Error("Unable to add release to your list");
  }

  return response.json();
};

export const fetchMyMusicList = async (): Promise<UserMusicListEntry[]> => {
  const response = await authFetch(`${import.meta.env.VITE_API_URL}/api/user-music-list`);

  if (!response.ok) {
    throw new Error("Unable to fetch your music list");
  }

  return response.json();
};

export const changeStatus = async (
  entryId: number,
  status: ListeningStatus
): Promise<UserMusicListEntry> => {
  const response = await authFetch(
    `${import.meta.env.VITE_API_URL}/api/user-music-list/${entryId}/status`,
    {
      method: "PATCH",
      body: JSON.stringify({ status }),
    }
  );

  if (!response.ok) {
    throw new Error("Unable to update list status");
  }

  return response.json();
};

export const removeFromList = async (entryId: number): Promise<void> => {
  const response = await authFetch(
    `${import.meta.env.VITE_API_URL}/api/user-music-list/${entryId}`,
    {
      method: "DELETE",
    }
  );

  if (!response.ok) {
    throw new Error("Unable to remove release from your list");
  }
};

export async function signInBackend(idToken: string): Promise<FirebaseSignInRes> {
  const response = await fetch(`${import.meta.env.VITE_API_URL}/api/auth/sign-in`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ idToken }),
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Backend sign-in failed");
  }

  return response.json();
}
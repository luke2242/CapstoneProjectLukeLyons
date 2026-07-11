import type { DiscogsDTO } from "../types/discogs";

const API_URL = `${import.meta.env.VITE_API_URL}/api/discogs`;

export async function getTrending(
  sortBy = "year",
  count = 10
): Promise<DiscogsDTO[]> {

  const response = await fetch(
    `${API_URL}/trending?sortBy=${sortBy}&count=${count}`
  );

  if (!response.ok) {
    throw new Error("Failed to fetch trending releases");
  }

  return response.json();
}
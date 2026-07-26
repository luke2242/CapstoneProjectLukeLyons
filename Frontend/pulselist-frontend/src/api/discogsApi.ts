import type { DiscogsDTO, DiscogsSearchResultDTO } from "../types/discogs";
import { authFetch } from "./authApi";

const API_URL = `${import.meta.env.VITE_API_URL}/api/discogs`;

export async function getTrending(
  sortBy = "month",
  count = 50
): Promise<DiscogsDTO[]> {

  const response = await authFetch(
    `${API_URL}/trending?sortBy=${sortBy}&count=${count}`
  );

  if (!response.ok) {
    throw new Error("Failed to fetch trending releases");
  }

  return response.json();
}

// Returns 50 search results per page for our search query
export async function getSearchResults(
  query: string,
  type: 'release' | 'master' | 'artist',
  page = 1,
  perPage = 50,
  signal?: AbortSignal
): Promise<DiscogsSearchResultDTO[]> {


  const response = await authFetch(
    `${API_URL}/search?q=${encodeURIComponent(query)}&type=${type}&page=${page}&perPage=${perPage}`,
    { signal }
  );

  if (!response.ok) {
    throw new Error("Failed to fetch search results");
  }

  const data: DiscogsSearchResultDTO[] = await response.json();

  return data;
}
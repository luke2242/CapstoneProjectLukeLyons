import type { DiscogsDTO, DiscogsSearchResultDTO } from "../types/discogs";

const API_URL = `${import.meta.env.VITE_API_URL}/api/discogs`;

export async function getTrending(
  sortBy = "year",
  count = 50
): Promise<DiscogsDTO[]> {

  const response = await fetch(
    `${API_URL}/trending?sortBy=${sortBy}&count=${count}`
  );

  if (!response.ok) {
    throw new Error("Failed to fetch trending releases");
  }

  return response.json();
}

// Returns 10 search results per page for our search query
export async function getSearchResults(
  query: string,
  type: "release",
  page = 1,
  perPage = 10,
  signal?: AbortSignal
): Promise<DiscogsSearchResultDTO[]> {
  const response = await fetch(
    `${API_URL}/search?q=${encodeURIComponent(query)}&type=${type}&page=${page}&perPage=${perPage}`,
    { signal }
  );

  if (!response.ok) {
    throw new Error("Failed to fetch search results");
  }

  const data: DiscogsSearchResultDTO[] = await response.json();

  return data;
}
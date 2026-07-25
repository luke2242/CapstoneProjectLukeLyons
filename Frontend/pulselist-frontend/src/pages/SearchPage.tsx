import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { useSearchDebounce } from "../hooks/useSearchDebounce";
import { getSearchResults } from "../api/discogsApi";
import type { DiscogsSearchResultDTO } from "../types/discogs";

export default function SearchPage() {
  const [searchTerm, setSearchTerm] = useState("");
  // Intended for pages later (50 results per page)
  const [page, setPage] = useState(1);

  // Every 500 millisceonds the search will be updated, rather than updating every keystroke
  const debouncedSearchTerm = useSearchDebounce(searchTerm, 500);

  // Use 
  const {
    data = [],
    isLoading,
    isError,
    error,
  } = useQuery<DiscogsSearchResultDTO[]>({
    queryKey: ["discogs-search", debouncedSearchTerm, page],
    queryFn: ({ signal }) =>
      // Inputs for get search result
      getSearchResults(
        debouncedSearchTerm,
        "release",
        page,
        10,
        signal
      ),
    // Trims the debounced search term and will only run this call if the search term has a length greater than 1
    enabled: debouncedSearchTerm.trim().length > 0,
    // Ensures if the user searches the same search term again that it's cached for at least 1 minute
    // This avoids unneccesary API calls
    staleTime: 60 * 1000
  });

  // In our return, we have a default placeholder if no images can be found for the discogs item
  // We also methods in place for loading, errors and if there's no search results found
  return (
    <div>
      <input
        type="text"
        placeholder="Search releases..."
        value={searchTerm}
        onChange={(e) => {
          setSearchTerm(e.target.value);
          setPage(1);
        }}
      />

      {!searchTerm.trim() && (
        <p>Start typing to search for releases.</p>
      )}

      {isLoading && <p>Loading releases...</p>}

      {isError && (
        <p>
          Failed to load releases.
          {error.message}
        </p>
      )}

      {!isLoading &&
        !isError &&
        searchTerm.trim() &&
        data.length === 0 && (
          <p>No releases found.</p>
        )}

      {!isLoading && !isError && data.length > 0 && (
        <div>
          {data.map((release) => (
            <div key={release.id}>
              {release.cover_image ? (
                <img
                  src={release.cover_image}
                  alt={release.title}
                  width={200}
                />
              ) : (
                <div
                  className="no-image-available"
                >
                  No image
                </div>
              )}

              <h3>{release.title}</h3>
              <p>Year: {release.year}</p>
              <p>Country: {release.country || "Unknown"}</p>
              
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
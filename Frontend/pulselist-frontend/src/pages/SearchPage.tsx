import { useState } from "react";
import {   useInfiniteQuery, type InfiniteData, useMutation, useQueryClient } from "@tanstack/react-query";
import { Alert, Box, Button, Card, CardContent, CardMedia, Grid, MenuItem, Paper, Select, Stack, TextField, Typography,} from "@mui/material";
import { useSearchDebounce } from "../hooks/useSearchDebounce";
import { getSearchResults } from "../api/discogsApi";
import { addToList } from "../api/listManagementApi";
import type { DiscogsSearchResultDTO } from "../types/discogs";
import { LISTENING_STATUSES, LISTENING_STATUS_LABELS, type ListeningStatus} from "../types/listStatus";
import PageLayout from "../components/PageLayout";

export default function SearchPage() {
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedStatuses, setSelectedStatuses] = useState<Record<number, ListeningStatus>>({});
  const queryClient = useQueryClient();

  const debouncedSearchTerm = useSearchDebounce(searchTerm, 500);

  const addToListMutation = useMutation({
    mutationFn: addToList,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["my-music-list"] });
    },
  });

  const getStatusForRelease = (releaseId: number): ListeningStatus => {
    return selectedStatuses[releaseId] ?? "WANT_TO_LISTEN";
  };

const {
    data,
    isLoading,
    isError,
    error,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  } = useInfiniteQuery<
    DiscogsSearchResultDTO[],
    Error,
    InfiniteData<DiscogsSearchResultDTO[]>,
    [string, string],
    number
  >({
    queryKey: ["discogs-search", debouncedSearchTerm],
    queryFn: ({ pageParam = 1, signal }) =>
      getSearchResults(debouncedSearchTerm, "release", pageParam, 50, signal),
    enabled: debouncedSearchTerm.trim().length > 0,
    staleTime: 60 * 1000,
    initialPageParam: 1,
    getNextPageParam: (lastPage, allPages) =>
      lastPage.length === 50 ? allPages.length + 1 : undefined,
  });

  const searchResults = data?.pages.flat() ?? [];


  return (
    <PageLayout
      showNavbar
      title="Search"
      subtitle="Find releases and save them to your personal PulseList."
    >
      <Paper sx={{ p: 3, mb: 3 }}>
        <TextField
          fullWidth
          placeholder="Search releases..."
          label="Search releases"
          value={searchTerm}
          onChange={(e) => {
            setSearchTerm(e.target.value);
          }}
        />
      </Paper>

      {!searchTerm.trim() ? <Typography>Start typing to search for releases.</Typography> : null}
      {isLoading ? <Typography>Loading releases...</Typography> : null}

      {isError ? (
        <Alert severity="error" sx={{ mb: 2 }}>
          Failed to load releases. {error.message}
        </Alert>
      ) : null}

      {!isLoading && !isError && searchTerm.trim() && searchResults.length === 0 ? (
        <Typography>No releases found.</Typography>
      ) : null}

      {!isLoading && !isError && searchResults.length > 0 ? (
        <Grid container spacing={2}>
          {searchResults.map((release) => {
            const imageUrl = release.cover_image || release.thumb;

            return (
              <Grid size={{ xs: 12, sm: 6, md: 4 }} key={release.id}>
                <Card sx={{ height: "100%" }}>
                  {imageUrl ? (
                    <CardMedia component="img" height="240" image={imageUrl} alt={release.title} />
                  ) : (
                    <Box
                      sx={{
                        height: 240,
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        color: "text.secondary",
                      }}
                    >
                      No image
                    </Box>
                  )}

                  <CardContent>
                    <Stack spacing={1.5}>
                      <Typography variant="h6">{release.title}</Typography>
                      <Typography variant="body2" sx={{ color: "text.secondary" }}>
                        Year: {release.year || "Unknown"}
                      </Typography>
                      <Typography variant="body2" sx={{ color: "text.secondary" }}>
                        Country: {release.country || "Unknown"}
                      </Typography>

                      <Stack direction="row" spacing={1.2} sx={{ alignItems: "center" }}>
                        <Typography variant="body2">Status</Typography>
                        <Select
                          size="small"
                          value={getStatusForRelease(release.id)}
                          onChange={(e) => {
                            setSelectedStatuses((prev) => ({
                              ...prev,
                              [release.id]: e.target.value as ListeningStatus,
                            }));
                          }}
                        >
                          {LISTENING_STATUSES.map((status) => (
                            <MenuItem key={status} value={status}>
                              {LISTENING_STATUS_LABELS[status]}
                            </MenuItem>
                          ))}
                        </Select>
                      </Stack>

                      <Button
                        variant="contained"
                        onClick={() => {
                          addToListMutation.mutate({
                            discogsReleaseId: release.id,
                            discogsTitle: release.title,
                            discogsArtist: release.title.split(" - ")[0] || "Unknown artist",
                            discogsCoverUrl: imageUrl || undefined,
                            status: getStatusForRelease(release.id),
                          });
                        }}
                      >
                        Add to list
                      </Button>
                    </Stack>
                  </CardContent>
                </Card>
              </Grid>
            );
          })}
        </Grid>
      ) : null}

      {!isLoading && !isError && hasNextPage ? (
        <Box sx={{ display: "flex", justifyContent: "center", mt: 3 }}>
          <Button variant="outlined" onClick={() => fetchNextPage()} disabled={isFetchingNextPage}>
            {isFetchingNextPage ? "Loading more..." : "Load more"}
          </Button>
        </Box>
      ) : null}
    </PageLayout>
  );
}

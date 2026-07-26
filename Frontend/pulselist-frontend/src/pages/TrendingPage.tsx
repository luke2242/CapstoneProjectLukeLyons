import { useState } from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import {
  Alert,
  Button,
  Card,
  CardContent,
  Grid,
  MenuItem,
  Select,
  Stack,
  Typography,
} from "@mui/material";
import type { DiscogsDTO } from "../types/discogs";
import { useTrendingReleases } from "../hooks/useTrendingReleases";
import { addToList } from "../api/listManagementApi";
import {
  LISTENING_STATUSES,
  LISTENING_STATUS_LABELS,
  type ListeningStatus,
} from "../types/listStatus";
import PageLayout from "../components/PageLayout";

export default function TrendingPage() {
  const [selectedStatuses, setSelectedStatuses] = useState<Record<number, ListeningStatus>>({});
  const queryClient = useQueryClient();

  const {
    data: albums = [],
    isLoading,
    error,
  } = useTrendingReleases();

  const addToListMutation = useMutation({
    mutationFn: addToList,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["my-music-list"] });
    },
  });

  const getStatusForAlbum = (albumId: number): ListeningStatus => {
    return selectedStatuses[albumId] ?? "WANT_TO_LISTEN";
  };

  return (
    <PageLayout
      showNavbar
      title="Trending"
      subtitle="Discover what's trending and save releases to your personal PulseList."
    >
      {isLoading ? <Typography>Loading releases...</Typography> : null}

      {error ? (
        <Alert severity="error" sx={{ mb: 2 }}>
          Failed to load releases.
        </Alert>
      ) : null}

      {!isLoading && !error && albums.length === 0 ? (
        <Typography>No trending releases found.</Typography>
      ) : null}

      {!isLoading && !error && albums.length > 0 ? (
        <Grid container spacing={2}>
          {albums.map((album: DiscogsDTO) => (
            <Grid size={{ xs: 12, sm: 6, md: 4 }} key={album.id}>
              <Card sx={{ height: "100%" }}>
                <CardContent>
                  <Stack spacing={1.5}>
                    <Typography variant="h6">{album.title}</Typography>
                    <Typography variant="body2" sx={{ color: "text.secondary" }}>
                      Year: {album.year || "Unknown"}
                    </Typography>
                    <Typography variant="body2" sx={{ color: "text.secondary" }}>
                      Country: {album.country || "Unknown"}
                    </Typography>

                    <Stack direction="row" spacing={1.2} sx={{ alignItems: "center" }}>
                      <Typography variant="body2">Status</Typography>
                      <Select
                        size="small"
                        value={getStatusForAlbum(album.id)}
                        onChange={(e) => {
                          setSelectedStatuses((prev) => ({
                            ...prev,
                            [album.id]: e.target.value as ListeningStatus,
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
                          discogsReleaseId: album.id,
                          discogsTitle: album.title,
                          discogsArtist: album.title.split(" - ")[0] || "Unknown artist",
                          status: getStatusForAlbum(album.id),
                        });
                      }}
                    >
                      Add to list
                    </Button>
                  </Stack>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      ) : null}
    </PageLayout>
  );
}

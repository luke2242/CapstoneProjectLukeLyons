import { useMemo, useState } from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { Alert, Avatar, Box, Button, Chip, Grid, MenuItem, Paper, Select, Stack, Typography } from "@mui/material";
import { changeStatus, fetchMyMusicList, removeFromList, type UserMusicListEntry } from "../api/listManagementApi";
import { useAuth } from "../config/authConfig";
import { LISTENING_STATUSES, LISTENING_STATUS_LABELS, type ListeningStatus } from "../types/listStatus";
import PageLayout from "../components/PageLayout";

type StatusFilter = "ALL" | ListeningStatus;

export default function AccountPage() {
  const { user, loading } = useAuth();
  const queryClient = useQueryClient();
  const [statusFilter, setStatusFilter] = useState<StatusFilter>("ALL");

  const {
    data: myMusicList = [],
    isLoading,
    isError,
    error,
  } = useQuery({
    queryKey: ["my-music-list"],
    queryFn: fetchMyMusicList,
    enabled: !loading && !!user,
  });

  const changeStatusMutation = useMutation({
    mutationFn: ({ entryId, status }: { entryId: number; status: ListeningStatus }) =>
      changeStatus(entryId, status),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["my-music-list"] });
    },
  });

  const removeEntryMutation = useMutation({
    mutationFn: (entryId: number) => removeFromList(entryId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["my-music-list"] });
    },
  });

  const statusCounts = useMemo(() => {
    return myMusicList.reduce<Record<ListeningStatus, number>>(
      (acc, entry) => {
        acc[entry.status] += 1;
        return acc;
      },
      {
        WANT_TO_LISTEN: 0,
        CURRENTLY_LISTENING: 0,
        LISTENED: 0,
        DROPPED: 0,
      }
    );
  }, [myMusicList]);

  const filteredEntries = useMemo(() => {
    if (statusFilter === "ALL") {
      return myMusicList;
    }

    return myMusicList.filter((entry) => entry.status === statusFilter);
  }, [myMusicList, statusFilter]);

  const isChangingStatusForEntry = (entry: UserMusicListEntry): boolean => {
    return changeStatusMutation.isPending && changeStatusMutation.variables?.entryId === entry.id;
  };

  const isRemovingEntry = (entry: UserMusicListEntry): boolean => {
    return removeEntryMutation.isPending && removeEntryMutation.variables === entry.id;
  };

  return (
    <PageLayout
      showNavbar
      title={`Welcome ${user?.displayName || user?.email || "User"}`}
      subtitle="Your personal music list, status tracking, and quick management tools."
    >
      <Grid container spacing={2} sx={{ mb: 3 }}>
        {LISTENING_STATUSES.map((status) => (
          <Grid size={{ xs: 12, sm: 6, md: 3 }} key={status}>
            <Paper sx={{ p: 2 }}>
              <Typography variant="body2" sx={{ color: "text.secondary" }}>
                {LISTENING_STATUS_LABELS[status]}
              </Typography>
              <Typography variant="h4">{statusCounts[status]}</Typography>
            </Paper>
          </Grid>
        ))}
      </Grid>

      <Paper sx={{ p: 2.5, mb: 3 }}>
        <Stack direction={{ xs: "column", sm: "row" }} spacing={1.5} sx={{ alignItems: "center" }}>
          <Typography>Filter by status</Typography>
          <Select
            size="small"
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value as StatusFilter)}
          >
            <MenuItem value="ALL">All statuses</MenuItem>
            {LISTENING_STATUSES.map((status) => (
              <MenuItem key={status} value={status}>
                {LISTENING_STATUS_LABELS[status]}
              </MenuItem>
            ))}
          </Select>
          <Box sx={{ flexGrow: 1 }} />
          <Chip label={`${filteredEntries.length} shown`} color="primary" />
        </Stack>
      </Paper>

      {isLoading ? <Typography>Loading your music list...</Typography> : null}

      {isError ? (
        <Alert severity="error" sx={{ mb: 2 }}>
          Failed to load music list: {error instanceof Error ? error.message : "Unknown error"}
        </Alert>
      ) : null}

      {changeStatusMutation.isError ? (
        <Alert severity="error" sx={{ mb: 2 }}>
          Failed to update status. {changeStatusMutation.error instanceof Error ? changeStatusMutation.error.message : "Unknown error"}
        </Alert>
      ) : null}

      {removeEntryMutation.isError ? (
        <Alert severity="error" sx={{ mb: 2 }}>
          Failed to remove entry. {removeEntryMutation.error instanceof Error ? removeEntryMutation.error.message : "Unknown error"}
        </Alert>
      ) : null}

      {!isLoading && !isError && myMusicList.length === 0 ? <Typography>Your list is empty.</Typography> : null}

      {!isLoading && !isError && myMusicList.length > 0 && filteredEntries.length === 0 ? (
        <Typography>No songs found for this status.</Typography>
      ) : null}

      <Stack spacing={1.5}>
        {filteredEntries.map((entry) => (
          <Paper key={entry.id} sx={{ p: 2 }}>
            <Stack direction={{ xs: "column", md: "row" }} spacing={2} sx={{ alignItems: { md: "center" } }}>
              <Avatar
                variant="rounded"
                src={entry.discogsCoverUrl || undefined}
                alt={`${entry.discogsTitle} cover`}
                sx={{ width: 64, height: 64, bgcolor: "rgba(250,235,215,0.1)" }}
              />

              <Box sx={{ flexGrow: 1 }}>
                <Typography variant="h6">{entry.discogsTitle}</Typography>
                <Typography sx={{ color: "text.secondary" }}>{entry.discogsArtist}</Typography>
              </Box>

              <Select
                size="small"
                value={entry.status}
                onChange={(e) => {
                  changeStatusMutation.mutate({
                    entryId: entry.id,
                    status: e.target.value as ListeningStatus,
                  });
                }}
                disabled={isChangingStatusForEntry(entry)}
              >
                {LISTENING_STATUSES.map((status) => (
                  <MenuItem key={status} value={status}>
                    {LISTENING_STATUS_LABELS[status]}
                  </MenuItem>
                ))}
              </Select>

              <Button
                variant="outlined"
                color="error"
                onClick={() => {
                  removeEntryMutation.mutate(entry.id);
                }}
                disabled={isRemovingEntry(entry)}
              >
                {isRemovingEntry(entry) ? "Removing..." : "Remove"}
              </Button>
            </Stack>
          </Paper>
        ))}
      </Stack>
    </PageLayout>
  );
}

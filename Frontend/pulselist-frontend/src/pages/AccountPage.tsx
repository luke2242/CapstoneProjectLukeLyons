import { useMemo, useState } from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  changeStatus,
  fetchMyMusicList,
  removeFromList,
  type UserMusicListEntry,
} from "../api/listManagementApi";
import { useAuth } from "../config/authConfig";
import {
  LISTENING_STATUSES,
  LISTENING_STATUS_LABELS,
  type ListeningStatus,
} from "../types/listStatus";

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
    return (
      changeStatusMutation.isPending &&
      changeStatusMutation.variables?.entryId === entry.id
    );
  };

  const isRemovingEntry = (entry: UserMusicListEntry): boolean => {
    return (
      removeEntryMutation.isPending && removeEntryMutation.variables === entry.id
    );
  };

  return (
    <div>
      <h1>Welcome {user?.displayName || user?.email || "User"}!</h1>
      <h2>Your music list</h2>

      <div>
        <strong>Status totals:</strong>
        <ul>
          {LISTENING_STATUSES.map((status) => (
            <li key={status}>
              {statusCounts[status]} songs in {LISTENING_STATUS_LABELS[status]}
            </li>
          ))}
        </ul>
      </div>

      <label htmlFor="account-status-filter">Filter by status: </label>
      <select
        id="account-status-filter"
        value={statusFilter}
        onChange={(e) => setStatusFilter(e.target.value as StatusFilter)}
      >
        <option value="ALL">All statuses</option>
        {LISTENING_STATUSES.map((status) => (
          <option key={status} value={status}>
            {LISTENING_STATUS_LABELS[status]}
          </option>
        ))}
      </select>

      {isLoading && <p>Loading your music list...</p>}
      {isError && (
        <p>
          Failed to load music list: {error instanceof Error ? error.message : "Unknown error"}
        </p>
      )}

      {changeStatusMutation.isError && (
        <p>
          Failed to update status.
          {changeStatusMutation.error instanceof Error
            ? changeStatusMutation.error.message
            : "Unknown error"}
        </p>
      )}

      {removeEntryMutation.isError && (
        <p>
          Failed to remove entry.
          {removeEntryMutation.error instanceof Error
            ? removeEntryMutation.error.message
            : "Unknown error"}
        </p>
      )}

      {!isLoading && !isError && myMusicList.length === 0 && <p>Your list is empty.</p>}

      {!isLoading && !isError && myMusicList.length > 0 && filteredEntries.length === 0 && (
        <p>No songs found for this status.</p>
      )}

      <ul>
        {filteredEntries.map((entry) => (
          <li
          >
            {entry.discogsCoverUrl ? (
              <img
                src={entry.discogsCoverUrl}
                alt={`${entry.discogsTitle} cover`}
                style={{ width: 48, height: 48, objectFit: "cover", borderRadius: 6 }}
              />
            ) : (
              <div
              />
            )}

            <div>
              <div>{entry.discogsTitle}</div>
              <div>{entry.discogsArtist}</div>
            </div>

            <div>
              <label htmlFor={`entry-status-${entry.id}`}>Status</label>
              <select
                id={`entry-status-${entry.id}`}
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
                  <option key={status} value={status}>
                    {LISTENING_STATUS_LABELS[status]}
                  </option>
                ))}
              </select>
            </div>

            <button
              onClick={() => {
                removeEntryMutation.mutate(entry.id);
              }}
              disabled={isRemovingEntry(entry)}
            >
              {isRemovingEntry(entry) ? "Removing..." : "Remove"}
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

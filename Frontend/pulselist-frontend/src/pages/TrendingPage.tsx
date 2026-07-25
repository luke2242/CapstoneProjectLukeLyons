import { useState } from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { DiscogsDTO } from "../types/discogs";
import { useTrendingReleases } from "../hooks/useTrendingReleases";
import { addToList } from "../api/listManagementApi";
import {
  LISTENING_STATUSES,
  LISTENING_STATUS_LABELS,
  type ListeningStatus,
} from "../types/listStatus";

export default function TrendingPage() {
  const [selectedStatuses, setSelectedStatuses] = useState<
    Record<number, ListeningStatus>
  >({});
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

  if (isLoading) {
    return <p>Loading releases...</p>;
  }

  if (error) {
    return <p>Failed to load releases</p>;
  }

  return (
    <div>
      {addToListMutation.isError && (
        <p>
          Failed to add release to list.
          {addToListMutation.error instanceof Error
            ? addToListMutation.error.message
            : "Unknown error"}
        </p>
      )}

      {albums.map((album: DiscogsDTO) => {
        const selectedStatus = getStatusForAlbum(album.id);
        const isAddingThisAlbum =
          addToListMutation.isPending &&
          addToListMutation.variables?.discogsReleaseId === album.id;

        return (
          <div key={album.id}>
            {album.image && <img src={album.image} alt={album.title} width={200} />}

            <p>{album.title}</p>

            <label htmlFor={`trending-status-${album.id}`}>Status</label>
            <select
              id={`trending-status-${album.id}`}
              value={selectedStatus}
              onChange={(e) => {
                setSelectedStatuses((prev) => ({
                  ...prev,
                  [album.id]: e.target.value as ListeningStatus,
                }));
              }}
            >
              {LISTENING_STATUSES.map((status) => (
                <option key={status} value={status}>
                  {LISTENING_STATUS_LABELS[status]}
                </option>
              ))}
            </select>

            <button
              onClick={() => {
                addToListMutation.mutate({
                  discogsReleaseId: album.id,
                  discogsTitle: album.title,
                  discogsArtist: album.title.split(" - ")[0] || "Unknown artist",
                  discogsCoverUrl: album.image || undefined,
                  status: selectedStatus,
                });
              }}
              disabled={isAddingThisAlbum}
            >
              {isAddingThisAlbum ? "Adding..." : "Add to list"}
            </button>
          </div>
        );
      })}
    </div>
  );
}

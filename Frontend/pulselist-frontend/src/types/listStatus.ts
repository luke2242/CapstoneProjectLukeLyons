export const LISTENING_STATUSES = [
  "WANT_TO_LISTEN",
  "CURRENTLY_LISTENING",
  "LISTENED",
  "DROPPED",
] as const;

export type ListeningStatus = (typeof LISTENING_STATUSES)[number];


// Mirrors listening statuses from our ENUM in backend
export const LISTENING_STATUS_LABELS: Record<ListeningStatus, string> = {
  WANT_TO_LISTEN: "Want to listen",
  CURRENTLY_LISTENING: "Currently listening",
  LISTENED: "Listened",
  DROPPED: "Dropped",
};

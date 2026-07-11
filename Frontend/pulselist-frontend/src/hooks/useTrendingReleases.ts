import { useQuery } from "@tanstack/react-query";
import { getTrending } from "../api/discogsApi";

export function useTrendingReleases() {
    return useQuery({
        queryKey: ["trending-releases"],
        queryFn: () => getTrending(),
        // Ensures that it updates every 10 minutes
        staleTime: 1000 * 60 * 10,
    });
}
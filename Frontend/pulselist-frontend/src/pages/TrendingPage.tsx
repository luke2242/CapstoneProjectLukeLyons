import type { DiscogsDTO } from "../types/discogs";
import { useTrendingReleases } from "../hooks/useTrendingReleases";

export default function TrendingPage() {

    // users our custom hook to retrieve trending releases from our API
    const {
        data: albums = [],
        isLoading,
        error
    } = useTrendingReleases();

    // Will be replaced by a loading spinner in the future
    if (isLoading) {
        return <p>Loading releases...</p>;
    }

    if (error) {
        return <p>Failed to load releases</p>;
    }


    return (
        <div>
            {albums.map((album: DiscogsDTO) => (
                <div key={album.id}>
                    {album.image && (
                        <img
                            src={album.image}
                            alt={album.title}
                            width={200}
                        />
                    )}

                    <p>{album.title}</p>
                </div>
            ))}
        </div>
    );
}
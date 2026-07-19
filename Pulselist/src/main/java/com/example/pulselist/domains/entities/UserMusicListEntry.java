package com.example.pulselist.domains.entities;

import com.example.pulselist.domains.enums.ListeningStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "user_music_list",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "discogs_release_id"})
)
public class UserMusicListEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "user_id", nullable = false)
    @Getter
    @Setter
    private Long userId;

    @Column(name = "discogs_release_id", nullable = false)
    @Getter
    @Setter
    private Long discogsReleaseId;

    @Column(name = "discogs_title")
    @Getter
    @Setter
    private String discogsTitle;

    @Column(name = "discogs_artist")
    @Getter
    @Setter
    private String discogsArtist;

    @Column(name = "discogs_cover_url")
    @Getter
    @Setter
    private String discogsCoverUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    @Setter
    private ListeningStatus status = ListeningStatus.WANT_TO_LISTEN;
}

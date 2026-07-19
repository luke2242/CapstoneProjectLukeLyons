package com.example.pulselist.domains.dto;

import com.example.pulselist.domains.enums.ListeningStatus;

public record AddToMusicListRequest(Long discogsReleaseId,
                                    String discogsTitle,
                                    String discogsArtist,
                                    String discogsCoverUrl,
                                    ListeningStatus status) {
}

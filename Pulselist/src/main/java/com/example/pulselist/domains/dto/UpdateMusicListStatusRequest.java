package com.example.pulselist.domains.dto;

import com.example.pulselist.domains.enums.ListeningStatus;

public record UpdateMusicListStatusRequest(ListeningStatus status) {
}

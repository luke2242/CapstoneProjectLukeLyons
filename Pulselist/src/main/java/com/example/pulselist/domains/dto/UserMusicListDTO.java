package com.example.pulselist.domains.dto;

import com.example.pulselist.domains.enums.ListeningStatus;
import lombok.Data;

@Data
public class UserMusicListDTO {
        private Long id;
        private Long userId;
        private Long musicId;
        private String name;
        private Long discogsId;
        private ListeningStatus listeningStatus;
}

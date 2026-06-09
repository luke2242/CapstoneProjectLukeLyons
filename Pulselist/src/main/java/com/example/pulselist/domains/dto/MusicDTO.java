package com.example.pulselist.domains.dto;

import lombok.Data;

@Data
public class MusicDTO {

    private long id;
    private long discogsId;
    private String name;
    private int releaseYear;
    private String discogsThumbImg;
    private String genre;

}

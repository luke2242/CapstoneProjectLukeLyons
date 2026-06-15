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

    public MusicDTO(String name, int releaseYear, String genre){
        this.name = name;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

}

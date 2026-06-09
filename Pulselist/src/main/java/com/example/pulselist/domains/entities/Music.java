package com.example.pulselist.domains.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // These fields may be updated as the project grows
    private long discogsId;
    private String name;
    private int releaseYear;
    private String discogsThumbImg;
    private String genre;


    public Music(){

    }

    public Music(long discogsId, String name){
        this.discogsId = discogsId;
        this.name = name;
    }

    public void setDiscogsID(long discogsId){
        this.discogsId = discogsId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setReleaseYear(int releaseYear){
        this.releaseYear = releaseYear;
    }

    public void setDiscogsThumbImg(String discogsThumbImg){
        this.discogsThumbImg = discogsThumbImg;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public long getDiscogsId(){
        return this.discogsId;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getReleaseYear(){
        return this.releaseYear;
    }

    public String getDiscogsThumbImg(){
        return this.discogsThumbImg;
    }

    public String getGenre(){
        return this.genre;
    }



}

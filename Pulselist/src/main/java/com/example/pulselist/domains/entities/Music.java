package com.example.pulselist.domains.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long discogsId;
    private String name;


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

    public long getDiscogsId(){
        return this.discogsId;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

}

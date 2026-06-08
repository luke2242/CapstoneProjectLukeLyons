package com.example.pulselist.domains.entities;

import com.example.pulselist.domains.enums.ListeningStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "user_music_list")
public class UserMusicList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private Music music;

    @Enumerated(EnumType.STRING)
    private ListeningStatus listeningStatus;

    public UserMusicList(){

    }

    public UserMusicList(User user, Music music, ListeningStatus listeningStatus){
        this.user = user;
        this.music = music;
        this.listeningStatus = listeningStatus;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setMusic(Music music){
        this.music = music;
    }

    public void setListeningStatus(ListeningStatus listeningStatus){
        this.listeningStatus = listeningStatus;
    }

    public User getUser(){
        return this.user;
    }

    public Music getMusic(){
        return this.music;
    }

    public ListeningStatus getListeningStatus(){
        return this.listeningStatus;
    }

}

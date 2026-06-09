package com.example.pulselist.service.services;

import com.example.pulselist.domains.dto.MusicDTO;
import com.example.pulselist.domains.entities.Music;
import com.example.pulselist.exceptions.InvalidMusicIDException;

import java.util.List;

public interface MusicService {
    List<Music> getAllMusic();
    Music getMusicById(Long id) throws InvalidMusicIDException;
    Music saveMusic(MusicDTO music);
    Music updateMusic(Music music, Long id);
    void deleteMusicById(Long id);
}

package com.example.pulselist.service.services;

import com.example.pulselist.domains.dto.MusicDTO;
import com.example.pulselist.domains.entities.Music;
import com.example.pulselist.exceptions.InvalidMusicIDException;

import java.util.List;

public interface MusicService {
    List<MusicDTO> getAllMusic();
    MusicDTO getMusicById(Long id) throws InvalidMusicIDException;
    MusicDTO saveMusic(MusicDTO music);
    MusicDTO updateMusic(MusicDTO music, Long id);
    void deleteMusicById(Long id);
}

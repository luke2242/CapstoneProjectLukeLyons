package com.example.pulselist.service.services;

import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicList;
import com.example.pulselist.exceptions.InvalidUserMusicListIDException;

import java.util.List;

public interface UserMusicListService {
    UserMusicListDTO save(UserMusicListDTO userMusicListDto);
    List<UserMusicListDTO> getAllMusicLists();
    UserMusicListDTO getUserMusicListById(Long id) throws InvalidUserMusicListIDException;
    UserMusicListDTO updateMusicList(Long id, UserMusicListDTO userMusicDto);
    void deleteMusicListById(Long id);
}

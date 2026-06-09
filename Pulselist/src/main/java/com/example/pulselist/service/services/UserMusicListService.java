package com.example.pulselist.service.services;

import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicList;

import java.util.List;

public interface UserMusicListService {
    UserMusicList save(UserMusicListDTO userMusicListDto);
    List<UserMusicListService> getAllMusicLists();
    UserMusicListService getUserMusicListById(Long id);
    UserMusicListService updateMusicList(Long id, UserMusicListDTO userMusicDto);
    void deleteMusicListById(Long id);
}

package com.example.pulselist.service.services;

import com.example.pulselist.domains.dto.AddToMusicListRequest;
import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicListEntry;
import com.example.pulselist.domains.enums.ListeningStatus;
import com.example.pulselist.exceptions.InvalidUserMusicListIDException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserMusicListService {
    UserMusicListDTO save(UserMusicListDTO userMusicListDto);
    List<UserMusicListDTO> getAllMusicLists();
    UserMusicListDTO getUserMusicListById(Long id) throws InvalidUserMusicListIDException;
    UserMusicListDTO updateMusicList(Long id, UserMusicListDTO userMusicDto);
    void deleteMusicListById(Long id);
    UserMusicListEntry addOrUpdate(Long userId, AddToMusicListRequest request);
    public UserMusicListEntry updateStatus(Long entryId, ListeningStatus status);
}

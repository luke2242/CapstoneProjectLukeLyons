package com.example.pulselist.service.services;

import com.example.pulselist.domains.dto.AddToMusicListRequest;
import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicListEntry;
import com.example.pulselist.domains.enums.ListeningStatus;
import com.example.pulselist.exceptions.InvalidUserMusicListIDException;

import java.util.List;

public interface UserMusicListService {
    UserMusicListDTO save(UserMusicListDTO userMusicListDto);
    void deleteMusicListById(Long id);
    UserMusicListEntry addOrUpdate(Long userId, AddToMusicListRequest request);
    UserMusicListEntry addOrUpdateByFirebaseUid(String firebaseUid, AddToMusicListRequest request);
    UserMusicListEntry updateStatus(Long entryId, ListeningStatus status);
    List<UserMusicListEntry> getMusicListByFirebaseUid(String firebaseUid);
}

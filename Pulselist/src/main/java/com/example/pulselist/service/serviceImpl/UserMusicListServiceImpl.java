package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicList;
import com.example.pulselist.domains.repositories.UserMusicListRepository;
import com.example.pulselist.service.services.UserMusicListService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserMusicListServiceImpl implements UserMusicListService {

    @Autowired
    private final UserMusicListRepository userMusicListRepo;

    public UserMusicListServiceImpl(UserMusicListRepository userMusicListRepo){
        this.userMusicListRepo = userMusicListRepo;
    }


    @Override
    public UserMusicList save(UserMusicListDTO userMusicListDto) {
        return userMusicListRepo.save(userMusicListDto);
    }

    @Override
    public List<UserMusicListService> getAllMusicLists() {
        return List.of();
    }

    @Override
    public UserMusicListService getUserMusicListById(Long id) {
        return null;
    }

    @Override
    public UserMusicListService updateMusicList(Long id, UserMusicListDTO userMusicListDto) {
        return null;
    }

    @Override
    public void deleteMusicListById(Long id) {
        userMusicListRepo.deleteById(id);
    }
}

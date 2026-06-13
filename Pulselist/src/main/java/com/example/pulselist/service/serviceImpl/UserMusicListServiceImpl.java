package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicList;
import com.example.pulselist.domains.repositories.UserMusicListRepository;
import com.example.pulselist.exceptions.InvalidUserMusicListIDException;
import com.example.pulselist.service.mappers.UserMusicListMapper;
import com.example.pulselist.service.services.UserMusicListService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserMusicListServiceImpl implements UserMusicListService {

    private final UserMusicListRepository userMusicListRepo;
    private final UserMusicListMapper userMusicListMapper;

    public UserMusicListServiceImpl(UserMusicListRepository userMusicListRepo, UserMusicListMapper userMusicListMapper){
        this.userMusicListRepo = userMusicListRepo;
        this.userMusicListMapper = userMusicListMapper;
    }

    @Override
    public UserMusicListDTO save(UserMusicListDTO userMusicListDto) {
        UserMusicList entity = userMusicListMapper.toEntity(userMusicListDto);
        return userMusicListMapper.toDto(entity);
    }

    @Override
    public List<UserMusicListDTO> getAllMusicLists() {
        return userMusicListRepo.findAll().stream().map(userMusicListMapper::toDto).toList();
    }

    @Override
    public UserMusicListDTO getUserMusicListById(Long id) throws InvalidUserMusicListIDException {
        UserMusicList userMusicList = userMusicListRepo.findById(id)
                .orElseThrow(() -> new InvalidUserMusicListIDException("Invalid user music list id, no user could be found"));

        UserMusicListDTO dto = userMusicListMapper.toDto(userMusicList);

        return dto;
    }

    @Override
    public UserMusicListDTO updateMusicList(Long id, UserMusicListDTO userMusicListDto) {

        UserMusicList userMusicListDB = userMusicListRepo.findById(id).get();

        return null;
    }

    @Override
    public void deleteMusicListById(Long id) {
        userMusicListRepo.deleteById(id);
    }
}

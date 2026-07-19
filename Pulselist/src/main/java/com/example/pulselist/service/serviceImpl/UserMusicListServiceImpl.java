package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.AddToMusicListRequest;
import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicList;
import com.example.pulselist.domains.entities.UserMusicListEntry;
import com.example.pulselist.domains.enums.ListeningStatus;
import com.example.pulselist.domains.repositories.UserMusicListEntryRepository;
import com.example.pulselist.domains.repositories.UserMusicListRepository;
import com.example.pulselist.exceptions.InvalidUserMusicListIDException;
import com.example.pulselist.service.mappers.UserMusicListMapper;
import com.example.pulselist.service.services.UserMusicListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMusicListServiceImpl implements UserMusicListService {

    private final UserMusicListRepository userMusicListRepo;
    private final UserMusicListMapper userMusicListMapper;
    private final UserMusicListEntryRepository userMusicListEntryRepository;

    public UserMusicListServiceImpl(UserMusicListRepository userMusicListRepo, UserMusicListMapper userMusicListMapper, UserMusicListEntryRepository userMusicListEntryRepository){
        this.userMusicListRepo = userMusicListRepo;
        this.userMusicListMapper = userMusicListMapper;
        this.userMusicListEntryRepository = userMusicListEntryRepository;
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

    @Override
    @Transactional
    public UserMusicListEntry addOrUpdate(Long userId, AddToMusicListRequest request) {
        return userMusicListEntryRepository.findByUserIdAndDiscogsReleaseId(userId, request.discogsReleaseId())
                .map(entry -> {
                    entry.setDiscogsTitle(request.discogsTitle());
                    entry.setDiscogsArtist(request.discogsArtist());
                    entry.setDiscogsCoverUrl(request.discogsCoverUrl());
                    entry.setStatus(request.status());
                    return userMusicListEntryRepository.save(entry);
                })
                .orElseGet(() -> {
                    UserMusicListEntry entry = new UserMusicListEntry();
                    entry.setUserId(userId);
                    entry.setDiscogsReleaseId(request.discogsReleaseId());
                    entry.setDiscogsTitle(request.discogsTitle());
                    entry.setDiscogsArtist(request.discogsArtist());
                    entry.setDiscogsCoverUrl(request.discogsCoverUrl());
                    entry.setStatus(request.status());
                    return userMusicListEntryRepository.save(entry);
                });
    }

    @Override
    @Transactional
    public UserMusicListEntry updateStatus(Long entryId, ListeningStatus status){

        UserMusicListEntry entry = userMusicListEntryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        entry.setStatus(status);
        return userMusicListEntryRepository.save(entry);
    }
}

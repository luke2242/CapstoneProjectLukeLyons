package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.AddToMusicListRequest;
import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.entities.UserMusicList;
import com.example.pulselist.domains.entities.UserMusicListEntry;
import com.example.pulselist.domains.enums.ListeningStatus;
import com.example.pulselist.domains.repositories.UserMusicListEntryRepository;
import com.example.pulselist.domains.repositories.UserMusicListRepository;
import com.example.pulselist.domains.repositories.UserRepository;
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
    private final UserRepository userRepository;

    public UserMusicListServiceImpl(
            UserMusicListRepository userMusicListRepo,
            UserMusicListMapper userMusicListMapper,
            UserMusicListEntryRepository userMusicListEntryRepository,
            UserRepository userRepository
    ) {
        this.userMusicListRepo = userMusicListRepo;
        this.userMusicListMapper = userMusicListMapper;
        this.userMusicListEntryRepository = userMusicListEntryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserMusicListDTO save(UserMusicListDTO userMusicListDto) {
        UserMusicList entity = userMusicListMapper.toEntity(userMusicListDto);
        UserMusicList savedEntity = userMusicListRepo.save(entity);
        return userMusicListMapper.toDto(savedEntity);
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
    public UserMusicListEntry addOrUpdateByFirebaseUid(
            String firebaseUid,
            AddToMusicListRequest request
    ) {
        User user = userRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addOrUpdate(user.getId(), request);
    }

    @Override
    @Transactional
    public UserMusicListEntry updateStatus(Long entryId, ListeningStatus status) {
        UserMusicListEntry entry = userMusicListEntryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        entry.setStatus(status);
        return userMusicListEntryRepository.save(entry);
    }

    @Override
    public List<UserMusicListEntry> getMusicListByFirebaseUid(String firebaseUid) {
        User user = userRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMusicListEntryRepository.findAllByUserIdOrderByIdDesc(user.getId());
    }
}

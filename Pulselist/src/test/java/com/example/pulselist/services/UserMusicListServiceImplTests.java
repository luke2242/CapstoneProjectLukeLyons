package com.example.pulselist.services;

import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicList;
import com.example.pulselist.domains.enums.ListeningStatus;
import com.example.pulselist.domains.repositories.UserMusicListEntryRepository;
import com.example.pulselist.domains.repositories.UserMusicListRepository;
import com.example.pulselist.domains.repositories.UserRepository;
import com.example.pulselist.service.mappers.UserMusicListMapper;
import com.example.pulselist.service.serviceImpl.UserMusicListServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMusicListServiceImplTests {

    @Mock
    private UserMusicListRepository userMusicListRepo;

    @Mock
    private UserMusicListMapper userMusicListMapper;

    @Mock
    private UserMusicListEntryRepository userMusicListEntryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserMusicListServiceImpl userMusicListServiceImpl;

    @Test
    void saveItemToMusicList_ShouldSaveAnItemToMusicList() {

        // Creates and input DTO and sets parameters
        UserMusicListDTO inputDto = new UserMusicListDTO();
        inputDto.setId(1L);
        inputDto.setMusicId(1L);
        inputDto.setName("Computer World");
        inputDto.setDiscogsId(2L);
        inputDto.setListeningStatus(ListeningStatus.LISTENED);

        // Entities for saving, mapping and ouputing the dtos
        UserMusicList mappedEntity = new UserMusicList();
        UserMusicList savedEntity = new UserMusicList();
        UserMusicListDTO outputDto = new UserMusicListDTO();

        // Sets parameters of output dto to be the same as input
        outputDto.setId(1L);
        outputDto.setMusicId(1L);
        outputDto.setName("Computer World");
        outputDto.setDiscogsId(2L);
        outputDto.setListeningStatus(ListeningStatus.LISTENED);

        // Maps the input dto to entity
        when(userMusicListMapper.toEntity(inputDto)).thenReturn(mappedEntity);

        // Saves the entity to the repo
        when(userMusicListRepo.save(mappedEntity)).thenReturn(savedEntity);

        // converts the saved entity to dto and changes it to output dto
        when(userMusicListMapper.toDto(savedEntity)).thenReturn(outputDto);

        // Saves result using service implementation
        UserMusicListDTO result = userMusicListServiceImpl.save(inputDto);

        // Assertions
        assertEquals("Computer World", result.getName());
        assertEquals(ListeningStatus.LISTENED, result.getListeningStatus());

        // Verifies mapping and saving is functioning as intended
        verify(userMusicListMapper).toEntity(inputDto);
        verify(userMusicListRepo).save(mappedEntity);
        verify(userMusicListMapper).toDto(savedEntity);
    }

    @Test
    void deletesItemInMusicListById_ShouldDeleteTheMusicItemFromUsersList(){

        userMusicListServiceImpl.deleteMusicListById(1L);

        verify(userMusicListRepo, times(1)).deleteById(1L);
    }

}
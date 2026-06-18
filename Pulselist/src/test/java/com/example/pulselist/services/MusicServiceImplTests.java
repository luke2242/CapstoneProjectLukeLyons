package com.example.pulselist.services;

import com.example.pulselist.domains.dto.MusicDTO;
import com.example.pulselist.domains.entities.Music;
import com.example.pulselist.domains.repositories.MusicRepository;
import com.example.pulselist.exceptions.InvalidMusicIDException;
import com.example.pulselist.firebase.FirebaseConfigPulseList;
import com.example.pulselist.service.mappers.MusicMapper;
import com.example.pulselist.service.serviceImpl.MusicServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicServiceImplTests {

    @Mock
    private MusicMapper musicMapper;

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private MusicServiceImpl musicServiceImpl;

    @Test
    void findMusicById_shouldReturnMusic() throws InvalidMusicIDException {

        Music music = new Music(2L, "Stone");

        MusicDTO dto = new MusicDTO("Stone", 1999, "Rock");
        dto.setId(2L);
        dto.setDiscogsId(2L);
        dto.setDiscogsThumbImg("thumb.jpg");

        // Attmepts to locate music
        when(musicRepository.findById(2L)).thenReturn(Optional.of(music));
        when(musicMapper.toDto(music)).thenReturn(dto);

        MusicDTO result = musicServiceImpl.getMusicById(2L);

        //Assertions
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(2L, result.getDiscogsId());
        assertEquals("Stone", result.getName());
        assertEquals(1999, result.getReleaseYear());
        assertEquals("Rock", result.getGenre());
        assertEquals("thumb.jpg", result.getDiscogsThumbImg());

        verify(musicRepository).findById(2L);
        verify(musicMapper).toDto(music);
    }

    // Will check if our throw works when an invalid id is entered
    @Test
    void getMusicById_invalidId_throwsInvalidMusicIDException() {
        assertThrows(
                InvalidMusicIDException.class,
                () -> musicServiceImpl.getMusicById(999L)
        );
    }

    @Test
    void deleteUserById_deletesTheUser(){

        musicServiceImpl.deleteMusicById(2L);

        verify(musicRepository, times(1)).deleteById(2L);
    }

}

package services;

import com.example.pulselist.domains.dto.MusicDTO;
import com.example.pulselist.domains.entities.Music;
import com.example.pulselist.domains.repositories.MusicRepository;
import com.example.pulselist.exceptions.InvalidMusicIDException;
import com.example.pulselist.service.mappers.MusicMapper;
import com.example.pulselist.service.serviceImpl.MusicServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        MusicDTO dto = new MusicDTO();
        dto.setDiscogsId(2L);
        dto.setName("Stone");

        // Attmepts to locate music
        when(musicRepository.findById(2L)).thenReturn(Optional.of(music));
        when(musicMapper.toDto(music)).thenReturn(dto);

        MusicDTO result = musicServiceImpl.getMusicById(2L);

        //Assertions
        assertNotNull(result);
        assertEquals(2L, result.getDiscogsId());
        assertEquals("Stone", result.getName());

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

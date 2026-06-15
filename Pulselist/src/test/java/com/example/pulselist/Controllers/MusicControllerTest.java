package com.example.pulselist.Controllers;

import com.example.pulselist.PulselistApplication;
import com.example.pulselist.domains.dto.MusicDTO;
import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.Music;
import com.example.pulselist.service.services.MusicService;
import com.example.pulselist.service.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PulselistApplication.class)
@AutoConfigureMockMvc
public class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MusicService musicService;

    @Test
    public void addUser_ShouldReturnAddedUserDTO() throws Exception {

        MusicDTO mockMusic = new MusicDTO("computer_world", 2000, "Electronic");

        mockMusic.setId(1L);
        mockMusic.setDiscogsId(1L);
        mockMusic.setDiscogsThumbImg("thumb.jpg");

        // We save a copy of the data to be posted, and will check if it's returned correctly
        Mockito.when(musicService.saveMusic(Mockito.any()))
                .thenReturn(mockMusic);

        // Attempts to post the data to our mock API endpoint
        mockMvc.perform(post("/api/music/addMusic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"name\":\"computer_world\",\"releaseYear\":"
                                        + mockMusic.getReleaseYear()
                                        + ",\"genre\":\"Electronic\"}"
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("computer_world"))
                .andExpect(jsonPath("$.genre").value("Electronic"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.discogsId").exists())
                .andExpect(jsonPath("$.discogsThumbImg").value("thumb.jpg"));

    }

    @Test
    public void getMusic_ShouldReturnAllMusicDTOs() throws Exception {

        MusicDTO mockMusic = new MusicDTO("computer_world", 2000, "Electronic");
        MusicDTO mockMusicTwo = new MusicDTO("computer_world_two", 2001, "Rock");

        mockMusic.setId(1L);
        mockMusic.setDiscogsId(1L);
        mockMusic.setDiscogsThumbImg("thumb.jpg");

        mockMusicTwo.setId(2L);
        mockMusicTwo.setDiscogsId(2L);
        mockMusicTwo.setDiscogsThumbImg("picture.jpg");

        List<MusicDTO> dtos = new ArrayList<>();

        dtos.add(mockMusic);
        dtos.add(mockMusicTwo);

        Mockito.when(musicService.getAllMusic())
                .thenReturn(dtos);

        mockMvc.perform(get("/api/music"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("computer_world"))
                .andExpect(jsonPath("$[0].genre").value("Electronic"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].discogsId").exists())
                .andExpect(jsonPath("$[0].discogsThumbImg").value("thumb.jpg"))
                .andExpect(jsonPath("$[1].name").value("computer_world_two"))
                .andExpect(jsonPath("$[1].genre").value("Rock"))
                .andExpect(jsonPath("$[1].id").exists())
                .andExpect(jsonPath("$[1].discogsId").exists())
                .andExpect(jsonPath("$[1].discogsThumbImg").value("picture.jpg"));
    }


    @Test
    public void getMusicById_shouldReturnTheMusicById() throws Exception {

        MusicDTO mockMusic = new MusicDTO("computer_world", 2000, "Electronic");

        mockMusic.setId(1L);
        mockMusic.setDiscogsId(1L);
        mockMusic.setDiscogsThumbImg("thumb.jpg");

        // Gets a copy of our musicDTO, to compare to the API endpoint
        Mockito.when(musicService.getMusicById(1L))
                .thenReturn(mockMusic);

        // Attempts to get music from the API
        mockMvc.perform(get("/api/music/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("computer_world"))
                .andExpect(jsonPath("$.genre").value("Electronic"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.discogsId").exists())
                .andExpect(jsonPath("$.discogsThumbImg").value("thumb.jpg"));
    }
}

package com.example.pulselist.Controllers;

import com.example.pulselist.controllers.UserMusicListController;
import com.example.pulselist.domains.entities.UserMusicListEntry;
import com.example.pulselist.domains.enums.ListeningStatus;
import com.example.pulselist.service.services.FirebaseAuthService;
import com.example.pulselist.service.services.UserMusicListService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserMusicListController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserMusicListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserMusicListService userMusicListService;

    @MockitoBean
    private FirebaseAuthService firebaseAuthService;

    @MockitoBean
    private FirebaseAuth firebaseAuth;

    @MockitoBean
    private FirebaseApp firebaseApp;

    @Test
    public void getMyList_ShouldReturnEntries() throws Exception {
        UserMusicListEntry entry = buildEntry(7L, ListeningStatus.WANT_TO_LISTEN);

        Mockito.when(userMusicListService.getMusicListByFirebaseUid(ArgumentMatchers.nullable(String.class)))
                .thenReturn(List.of(entry));

        mockMvc.perform(get("/api/user-music-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(7))
                .andExpect(jsonPath("$[0].discogsTitle").value("Homework"))
                .andExpect(jsonPath("$[0].status").value("WANT_TO_LISTEN"));
    }

    @Test
    public void add_ShouldCreateOrUpdateEntry() throws Exception {
        UserMusicListEntry entry = buildEntry(8L, ListeningStatus.CURRENTLY_LISTENING);

        Mockito.when(userMusicListService.addOrUpdateByFirebaseUid(
                        ArgumentMatchers.nullable(String.class),
                        ArgumentMatchers.any()))
                .thenReturn(entry);

        mockMvc.perform(post("/api/user-music-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "discogsReleaseId": 249504,
                                  "discogsTitle": "Homework",
                                  "discogsArtist": "Daft Punk",
                                  "discogsCoverUrl": "https://img",
                                  "status": "CURRENTLY_LISTENING"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.discogsArtist").value("Daft Punk"))
                .andExpect(jsonPath("$.status").value("CURRENTLY_LISTENING"));
    }

    @Test
    public void setStatus_ShouldUpdateEntryStatus() throws Exception {
        UserMusicListEntry entry = buildEntry(9L, ListeningStatus.LISTENED);

        Mockito.when(userMusicListService.updateStatus(9L, ListeningStatus.LISTENED))
                .thenReturn(entry);

        mockMvc.perform(patch("/api/user-music-list/9/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"LISTENED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.status").value("LISTENED"));
    }

    @Test
    public void removeEntry_ShouldDeleteEntry() throws Exception {
        mockMvc.perform(delete("/api/user-music-list/10"))
                .andExpect(status().isOk());

        Mockito.verify(userMusicListService).deleteMusicListById(10L);
    }

    private static UserMusicListEntry buildEntry(Long id, ListeningStatus status) {
        UserMusicListEntry entry = new UserMusicListEntry();
        entry.setId(id);
        entry.setUserId(1L);
        entry.setDiscogsReleaseId(249504L);
        entry.setDiscogsTitle("Homework");
        entry.setDiscogsArtist("Daft Punk");
        entry.setDiscogsCoverUrl("https://img");
        entry.setStatus(status);
        return entry;
    }
}

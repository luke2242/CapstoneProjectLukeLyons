package com.example.pulselist.Controllers;

import com.example.pulselist.controllers.UserController;
import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.service.services.FirebaseAuthService;
import com.example.pulselist.service.services.UserService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private FirebaseAuth firebaseAuth;

    @MockitoBean
    private FirebaseApp firebaseApp;

    @MockitoBean
    private FirebaseAuthService firebaseAuthService;

    @Test
    public void addUser_ShouldReturnAddedUserDTO() throws Exception {

        UserDTO mockUser = new UserDTO("johnsmith");
        mockUser.setId(1L);

        Mockito.when(userService.saveUser(Mockito.any()))
                .thenReturn(mockUser);

        mockMvc.perform(post("/api/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"johnsmith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("johnsmith"))
                .andExpect(jsonPath("$.id").exists());

    }

    @Test
    public void getUserById_shouldReturnTheUserById() throws Exception {

        UserDTO mockUser = new UserDTO("johnnyt");
        mockUser.setId(1L);

        Mockito.when(userService.getUserById(1L))
                .thenReturn(mockUser);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("johnnyt"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser
    public void getUsers_ShouldReturnAllUsers() throws Exception {

        UserDTO mockUserOne = new UserDTO("johnnyt");
        mockUserOne.setId(1L);

        UserDTO mockUserTwo = new UserDTO("johnnyj");
        mockUserTwo.setId(2L);

        List<UserDTO> dtos = new ArrayList<>();
        dtos.add(mockUserOne);
        dtos.add(mockUserTwo);

        Mockito.when(userService.getUsers())
                .thenReturn(dtos);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("johnnyt"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].username").value("johnnyj"))
                .andExpect(jsonPath("$[1].id").value(2L));
    }
}

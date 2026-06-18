package com.example.pulselist.services;

import com.example.pulselist.domains.dto.CreateUserDTO;
import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.repositories.UserRepository;
import com.example.pulselist.exceptions.InvalidUserIDException;
import com.example.pulselist.firebase.FirebaseConfigPulseList;
import com.example.pulselist.service.mappers.UserMapper;
import com.example.pulselist.service.mappers.UserMapperImpl;
import com.example.pulselist.service.serviceImpl.UserServiceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
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
class UserServiceImplTests {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private FirebaseAuth firebaseAuth;


    @Test
    void saveUser_shouldSaveAndReturnUserDto() throws FirebaseAuthException {

        // Creates a mock createUserDTO
        CreateUserDTO createDto = new CreateUserDTO();

        createDto.setEmail("fakeemail@fake.ie");
        createDto.setUsername("newuser1");
        createDto.setPassword("nopassword");

        // Gets a mock user record class to user from firebase auth
        UserRecord userRecord = mock(UserRecord.class);

        // Attempts to get a Uid and returns it to us
        when(userRecord.getUid()).thenReturn("fakeuid");

        // Calls a create user request from firebase auth, and returns a user record
        when(firebaseAuth.createUser(any(UserRecord.CreateRequest.class)))
                .thenReturn(userRecord);

        // New user entity with the same param's as the createUserDTO
        User savedUser = new User();
        savedUser.setFirebaseUid("fakeuid");
        savedUser.setEmail("fakeemail@fake.ie");
        savedUser.setUsername("newuser1");

        // Saves it in our repository and returns a savedUser dto
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Our excepted output dto
        UserDTO expectedDto = new UserDTO();
        expectedDto.setEmail("fakeemail@fake.ie");
        expectedDto.setUsername("newuser1");

        // Converts the User Entity to a DTO and tests if it returns the same values
        when(userMapper.toDto(savedUser)).thenReturn(expectedDto);

        UserDTO result = userServiceImpl.saveUser(createDto);

        // We assert the values
        assertEquals("fakeemail@fake.ie", result.getEmail());
        assertEquals("newuser1", result.getUsername());

        // And verify
        verify(firebaseAuth).createUser(any(UserRecord.CreateRequest.class));
        verify(userRepository).save(any(User.class));

    }


    @Test
    void findUserById_shouldReturnUser() throws InvalidUserIDException {

        User user = new User("fakeID", "JOHNSMITH67");

        // Mock repository behavior
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        UserDTO result = userServiceImpl.getUserById(2L);

        assertNotNull(result);
        assertEquals("JOHNSMITH67", result.getUsername());

        verify(userRepository).findById(2L);
    }

    @Test
    void deleteUserById_deletesTheUser(){

        userServiceImpl.deleteUserById(2L);

        verify(userRepository, times(1)).deleteById(2L);
    }


    @Test
    void getUserById_invalidId_throwsInvalidUserIDException() {
        assertThrows(
                InvalidUserIDException.class,
                () -> userServiceImpl.getUserById(999L)
        );
    }


}
package services;

import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.repositories.UserRepository;
import com.example.pulselist.exceptions.InvalidUserIDException;
import com.example.pulselist.service.mappers.UserMapper;
import com.example.pulselist.service.mappers.UserMapperImpl;
import com.example.pulselist.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void saveUser_shouldSaveAndReturnUserDto(){

        UserDTO input = new UserDTO();
        User mappedEntity = new User();
        User savedEntity = new User();
        UserDTO returned = new UserDTO();

        when(userMapper.toEntity(input)).thenReturn(mappedEntity);
        when(userRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(userMapper.toDto(savedEntity)).thenReturn(returned);

        UserDTO result = userServiceImpl.saveUser(input);

        assertEquals(returned, result);
        verify(userMapper).toEntity(input);
        verify(userRepository).save(mappedEntity);
        verify(userMapper).toDto(savedEntity);

    }


    @Test
    void findUserById_shouldReturnUser() throws InvalidUserIDException {

        User user = new User("fakeID", "JOHNSMITH67");

        userRepository.save(user);

        // Attempts to find the user added by ID
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        UserDTO result = userServiceImpl.getUserById(2L);

        //Assertions
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
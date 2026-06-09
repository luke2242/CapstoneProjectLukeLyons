package services;

import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.repositories.UserRepository;
import com.example.pulselist.exceptions.InvalidMusicIDException;
import com.example.pulselist.exceptions.InvalidUserIDException;
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
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void findUserById_shouldReturnUser() throws InvalidUserIDException {

        User user = new User("fakeID", "JOHNSMITH67");

        // Attempts to find the user added by ID
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        User result = userServiceImpl.getUserById(2L);

        //Assertions
        assertNotNull(result);
        assertEquals("fakeID", result.getFirebaseUid());
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
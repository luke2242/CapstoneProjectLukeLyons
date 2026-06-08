package services;

import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.repositories.UserRepository;
import com.example.pulselist.service.InvalidUserIDException;
import com.example.pulselist.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findUserById_shouldReturnUser() throws InvalidUserIDException {

        User user = new User("fakeID", "JOHNSMITH67");

        // Attempts to find the user added by ID
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(2L);

        //Assertions
        assertNotNull(result);
        assertEquals("fakeID", result.getFirebaseUid());
        assertEquals("JOHNSMITH67", result.getUsername());

        verify(userRepository).findById(2L);
    }

    @Test
    void deleteUserById_deletesTheUser(){

        userService.deleteUserById(2L);

        verify(userRepository, times(1)).deleteById(2L);
    }

}
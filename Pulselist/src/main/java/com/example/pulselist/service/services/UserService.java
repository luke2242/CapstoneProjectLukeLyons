package com.example.pulselist.service.services;

import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.exceptions.InvalidUserIDException;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(Long id) throws InvalidUserIDException;
    User saveUser(UserDTO userDto);
    User updateUser(User user, Long userId);
    void deleteUserById(Long id);
}

package com.example.pulselist.controllers;

import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.exceptions.InvalidUserIDException;
import com.example.pulselist.service.serviceImpl.UserServiceImpl;
import com.example.pulselist.service.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Utilises business logic in user service, to return users on this link
    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws InvalidUserIDException {
        UserDTO userDto = userService.getUserById(id);
        return  ResponseEntity.ok(userDto);
    }

}

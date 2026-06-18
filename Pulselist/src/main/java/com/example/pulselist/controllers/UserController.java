package com.example.pulselist.controllers;

import com.example.pulselist.domains.dto.CreateUserDTO;
import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.exceptions.InvalidUserIDException;
import com.example.pulselist.service.serviceImpl.UserServiceImpl;
import com.example.pulselist.service.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Utilises business logic in user service, to return users on this link
    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }

    // Returns a user if the id exists
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws InvalidUserIDException {
        UserDTO userDto = userService.getUserById(id);
        return  ResponseEntity.ok(userDto);
    }

    // Posts user to our DB
    @PostMapping("/addUser")
    public UserDTO addUser(@RequestBody CreateUserDTO newUser){
        return userService.saveUser(newUser);
    }

    // Updates User
    @PutMapping("/users/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO dto){
        UserDTO saved = userService.updateUser(dto, id);
        return saved;
    }

    // Deletes user by id
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByID(@PathVariable Long id){
        userService.deleteUserById(id);
    }



}

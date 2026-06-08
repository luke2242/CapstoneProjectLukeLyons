package com.example.pulselist.controllers;

import com.example.pulselist.domains.entities.User;
import com.example.pulselist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    // Utilises business logic in user service, to return users on this link
    @RequestMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }
}

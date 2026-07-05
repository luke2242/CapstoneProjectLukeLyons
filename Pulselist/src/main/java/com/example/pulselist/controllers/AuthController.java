package com.example.pulselist.controllers;// AuthController.java
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/auth/me")
    public Map<String, Object> me(@AuthenticationPrincipal String uid) {
        return Map.of("uid", uid);
    }
}
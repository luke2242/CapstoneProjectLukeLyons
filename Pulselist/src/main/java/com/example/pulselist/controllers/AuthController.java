package com.example.pulselist.controllers;// AuthController.java
import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.records.FirebaseSignInReq;
import com.example.pulselist.records.FirebaseSignInRes;
import com.example.pulselist.service.services.FirebaseAuthService;
import com.example.pulselist.service.services.UserService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final FirebaseAuthService firebaseAuthService;
    private final UserService userService;

    public AuthController(FirebaseAuthService firebaseAuthService, UserService userService) {
        this.firebaseAuthService = firebaseAuthService;
        this.userService = userService;
    }

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal String uid) {
        return Map.of("uid", uid);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<FirebaseSignInRes> signIn(@Valid @RequestBody FirebaseSignInReq request) {
        FirebaseToken token = firebaseAuthService.verifyToken(request.getIdToken());
        UserDTO userDto = userService.findOrCreateUser(token.getUid(), token.getEmail(), token.getName());
        return ResponseEntity.ok(new FirebaseSignInRes(userDto.getId(), token.getUid()));
    }
}
package com.example.pulselist.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class FakeFirebaseAuthService {

    public String authenticate(String email, String password) {

        if (email.equals("fake@fake.com")
                && password.equals("test12")) {
            return "test-firebase-uid";
        }

        throw new RuntimeException("Invalid credentials");
    }
}
package com.example.pulselist.exceptions;

import com.google.firebase.auth.FirebaseAuthException;

public class InvalidFirebaseTokenException extends RuntimeException {
    public InvalidFirebaseTokenException(String message, FirebaseAuthException ex) {
        super(message, ex);
    }
}

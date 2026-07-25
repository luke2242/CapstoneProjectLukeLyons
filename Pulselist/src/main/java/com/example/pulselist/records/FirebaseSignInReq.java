package com.example.pulselist.records;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record FirebaseSignInReq(
        @Getter
    @NotBlank String idToken

) { }

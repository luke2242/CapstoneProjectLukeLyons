package com.example.pulselist.errors;

import lombok.Getter;
import lombok.Setter;

public class APIError {

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String message;

    public APIError(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
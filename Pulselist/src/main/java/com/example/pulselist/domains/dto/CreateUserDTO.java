package com.example.pulselist.domains.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// This DTO is specifically for incoming user data and does NOT save this info to the DB
@Data
@NoArgsConstructor
public class CreateUserDTO {
    private String email;
    private String password;
    private String username;


}

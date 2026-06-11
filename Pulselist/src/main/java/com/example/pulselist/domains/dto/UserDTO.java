package com.example.pulselist.domains.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;

    public UserDTO(String username){
        this.username = username;
    }
}

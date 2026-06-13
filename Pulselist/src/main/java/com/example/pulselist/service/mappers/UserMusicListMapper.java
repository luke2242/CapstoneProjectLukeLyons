package com.example.pulselist.service.mappers;

import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMusicListMapper {

    UserMusicListDTO toDto(UserMusicList entity);
    UserMusicList toEntity(UserMusicListDTO dto);
}


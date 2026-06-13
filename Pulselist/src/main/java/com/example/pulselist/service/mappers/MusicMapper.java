package com.example.pulselist.service.mappers;

import com.example.pulselist.domains.dto.MusicDTO;
import com.example.pulselist.domains.entities.Music;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MusicMapper {

    MusicDTO toDto(Music entity);
    Music toEntity(MusicDTO dto);

}

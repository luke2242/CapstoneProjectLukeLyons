package com.example.pulselist.domains.repositories;

import com.example.pulselist.domains.dto.MusicDTO;
import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

    Music save(MusicDTO musicDto);

}

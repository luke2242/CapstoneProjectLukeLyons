package com.example.pulselist.domains.repositories;

import com.example.pulselist.domains.entities.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Integer> {
}

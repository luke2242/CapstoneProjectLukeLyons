package com.example.pulselist.domains.repositories;

import com.example.pulselist.domains.entities.UserMusicList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMusicListRepository extends JpaRepository<UserMusicList, Integer> {
}

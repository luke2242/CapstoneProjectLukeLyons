package com.example.pulselist.domains.repositories;

import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMusicListRepository extends JpaRepository<UserMusicList, Long> {

    UserMusicList save(UserMusicListDTO userMusicListDto);
}

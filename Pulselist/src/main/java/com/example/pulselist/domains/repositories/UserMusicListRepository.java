package com.example.pulselist.domains.repositories;

import com.example.pulselist.domains.dto.UserMusicListDTO;
import com.example.pulselist.domains.entities.UserMusicList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMusicListRepository extends JpaRepository<UserMusicList, Long> {

    UserMusicList save(UserMusicListDTO userMusicListDto);
    Optional<UserMusicList> findByUser_IdAndMusic_Id(Long userId, Long musicId);
    List<UserMusicList> findByUser_Id(Long userId);
}

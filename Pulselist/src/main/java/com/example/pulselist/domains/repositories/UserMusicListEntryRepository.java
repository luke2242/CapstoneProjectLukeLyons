package com.example.pulselist.domains.repositories;

import com.example.pulselist.domains.entities.UserMusicListEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMusicListEntryRepository extends JpaRepository<UserMusicListEntry, Long> {
    Optional<UserMusicListEntry> findByUserIdAndDiscogsReleaseId(Long userId, Long discogsReleaseId);
    List<UserMusicListEntry> findAllByUserIdOrderByIdDesc(Long userId);
}

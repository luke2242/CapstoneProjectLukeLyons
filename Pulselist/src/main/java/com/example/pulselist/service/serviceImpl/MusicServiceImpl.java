package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.MusicDTO;
import com.example.pulselist.domains.entities.Music;
import com.example.pulselist.domains.repositories.MusicRepository;
import com.example.pulselist.exceptions.InvalidMusicIDException;
import com.example.pulselist.service.mappers.MusicMapper;
import com.example.pulselist.service.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private final MusicRepository musicRepo;

    @Autowired
    private final MusicMapper musicMapper;

    public MusicServiceImpl(MusicRepository musicRepo, MusicMapper musicMapper){
        this.musicRepo = musicRepo;
        this.musicMapper = musicMapper;
    }

    // Returns list all of music available
    @Override
    public List<MusicDTO> getAllMusic() {
        return musicRepo.findAll().stream().map(musicMapper::toDto).toList();
    }


    // Attempts to find music by id in database
    @Override
    public MusicDTO getMusicById(Long id) throws InvalidMusicIDException {
        Music music = musicRepo.findById(id)
                .orElseThrow(() -> new InvalidMusicIDException("Invalid music ID, music could not be found."));

        MusicDTO dto = musicMapper.toDto(music);

        return dto;
    }

    @Override
    public MusicDTO saveMusic(MusicDTO music) {
        Music entity = musicMapper.toEntity(music);
        return musicMapper.toDto(musicRepo.save(entity));
    }

    @Override
    public MusicDTO updateMusic(MusicDTO music, Long id) {

        // Music entity in the db
        Music musicDB = musicRepo.findById(id).get();

        // Ensures values are populated
        if (Objects.nonNull(music.getDiscogsId())) {
            musicDB.setDiscogsID(music.getDiscogsId());
        }

        if(Objects.nonNull(music.getName()) && !"".equalsIgnoreCase(music.getName())){
            musicDB.setName(music.getName());
        }

        if(Objects.nonNull(music.getReleaseYear())){
            musicDB.setReleaseYear(music.getReleaseYear());
        }

        if(Objects.nonNull(music.getDiscogsThumbImg()) && !"".equalsIgnoreCase(music.getDiscogsThumbImg())){
            musicDB.setDiscogsThumbImg(music.getDiscogsThumbImg());
        }

        if(Objects.nonNull(music.getGenre()) && !"".equalsIgnoreCase(music.getGenre())){
            musicDB.setGenre(music.getGenre());
        }

        musicRepo.save(musicDB);

        MusicDTO dto = musicMapper.toDto(musicDB);

        return dto;

    }

    @Override
    public void deleteMusicById(Long id) {
        musicRepo.deleteById(id);
    }
}

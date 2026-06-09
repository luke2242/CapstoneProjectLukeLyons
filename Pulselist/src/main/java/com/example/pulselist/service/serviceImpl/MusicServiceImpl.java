package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.entities.Music;
import com.example.pulselist.domains.repositories.MusicRepository;
import com.example.pulselist.exceptions.InvalidMusicIDException;
import com.example.pulselist.service.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private final MusicRepository musicRepo;

    public MusicServiceImpl(MusicRepository musicRepo){
        this.musicRepo = musicRepo;
    }

    // Returns list all of music available
    @Override
    public List<Music> getAllMusic() {
        return musicRepo.findAll();
    }


    // Attempts to find music by id in database
    @Override
    public Music getMusicById(Long id) throws InvalidMusicIDException {
        return musicRepo.findById(id)
                .orElseThrow(() -> new InvalidMusicIDException("Invalid music ID, music could not be found."));
    }

    @Override
    public Music saveMusic(Music music) {
        return musicRepo.save(music);
    }

    @Override
    public Music updateMusic(Music music, Long id) {

        // Music entity in the db
        Music musicDB = musicRepo.findById(id).get();

        // Ensures values are populated
        if(Objects.nonNull(music.getDiscogsId())){
            musicDB.setDiscogsID(musicDB.getDiscogsId());
        }

        if(Objects.nonNull(music.getName()) && !"".equalsIgnoreCase(music.getName())){
            musicDB.setName(music.getName());
        }

        return musicRepo.save(music);

    }

    @Override
    public void deleteMusicById(Long id) {
        musicRepo.deleteById(id);
    }
}

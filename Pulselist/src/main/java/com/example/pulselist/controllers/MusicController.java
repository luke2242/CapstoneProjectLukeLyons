package com.example.pulselist.controllers;

import com.example.pulselist.domains.dto.MusicDTO;
import com.example.pulselist.exceptions.InvalidMusicIDException;
import com.example.pulselist.service.services.MusicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
@CrossOrigin
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService){
        this.musicService = musicService;
    }

    @GetMapping("")
    public List<MusicDTO> getMusic(){
        return musicService.getAllMusic();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getMusicByID(@PathVariable Long id) throws InvalidMusicIDException {
        MusicDTO dto = musicService.getMusicById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/addMusic")
    public MusicDTO addMusic(@RequestBody MusicDTO dto){
        return musicService.saveMusic(dto);
    }

    @PutMapping("/music/{id}")
    public MusicDTO updateMusic(@PathVariable Long id, @RequestBody MusicDTO dto){
        MusicDTO saved = musicService.updateMusic(dto, id);
        return saved;
    }

    @DeleteMapping("/music/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        musicService.deleteMusicById(id);
    }


}

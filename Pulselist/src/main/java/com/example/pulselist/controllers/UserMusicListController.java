package com.example.pulselist.controllers;

import com.example.pulselist.domains.dto.AddToMusicListRequest;
import com.example.pulselist.domains.dto.UpdateMusicListStatusRequest;
import com.example.pulselist.domains.entities.UserMusicListEntry;
import com.example.pulselist.service.services.UserMusicListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-music-list")
@CrossOrigin
public class UserMusicListController {


    private final UserMusicListService userMusicListService;

    public UserMusicListController(UserMusicListService userMusicListService){
        this.userMusicListService = userMusicListService;
    }

    @PostMapping
    public UserMusicListEntry add(@RequestBody AddToMusicListRequest req){
        // Will be replaced by auth user id
        Long currentUserId = 1L;
        return userMusicListService.addOrUpdate(currentUserId, req);
    }

    @PatchMapping("/{entryId}/status")
    public UserMusicListEntry setStatus(@PathVariable Long entryId, @RequestBody UpdateMusicListStatusRequest req){
        return userMusicListService.updateStatus(entryId, req.status());
    }

    @DeleteMapping("/{entryId}")
    public void removeEntry(@PathVariable Long entryId){
        userMusicListService.deleteMusicListById(entryId);
    }

}

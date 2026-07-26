package com.example.pulselist.controllers;

import com.example.pulselist.domains.dto.DiscogsDTO;
import com.example.pulselist.domains.dto.DiscogsSearchDTO;
import com.example.pulselist.service.services.DiscogsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discogs")
@CrossOrigin(origins = "http://localhost:5173")
public class DiscogsController {

    private final DiscogsService discogsService;

    public DiscogsController(DiscogsService discogsService){
        this.discogsService = discogsService;
    }

    @GetMapping("/search")
    public List<DiscogsSearchDTO> searchCatalog(
            @RequestParam String q,
            @RequestParam(defaultValue = "release") String type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int perPage){

        return discogsService.searchCatalog(q, type);
    }

    @GetMapping("/trending")
    public List<DiscogsDTO> trendingReleases(
            @RequestParam(defaultValue = "year") String sortBy,
            @RequestParam(defaultValue = "50") int count) {

        return discogsService.trendingReleases(sortBy, count);
    }


}

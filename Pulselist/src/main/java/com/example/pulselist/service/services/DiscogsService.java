package com.example.pulselist.service.services;

import com.example.pulselist.domains.dto.DiscogsDTO;

import java.util.List;

public interface DiscogsService {

    List<DiscogsDTO> searchCatalog(String q, String type);
    List<DiscogsDTO> trendingReleases(String sortBy, int count);

}

package com.example.pulselist.service.services;

import com.example.pulselist.domains.dto.DiscogsDTO;
import com.example.pulselist.domains.dto.DiscogsSearchDTO;

import java.util.List;

public interface DiscogsService {

    List<DiscogsSearchDTO> searchCatalog(String q, String type, int page, int perPage);
    List<DiscogsDTO> trendingReleases(String sortBy, int count);

}

package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.DiscogsDTO;
import com.example.pulselist.domains.dto.DiscogsSearchDTO;
import com.example.pulselist.domains.dto.DiscogsSearchResultsDTO;
import com.example.pulselist.service.services.DiscogsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class DiscogsServiceImpl implements DiscogsService {

    private static final int MAX_SEARCH_PAGE_SIZE = 50;
    private static final int MAX_TRENDING_COUNT = 100;

    public final RestTemplate discogsRestTemplate;

    public DiscogsServiceImpl(RestTemplate discogsRestTemplate) {
        this.discogsRestTemplate = discogsRestTemplate;
    }

    @Override
    public List<DiscogsSearchDTO> searchCatalog(String q, String type, int page, int perPage) {

        // This helps us reduce the rate limits for discogs API
        int safePage = Math.max(page, 1);
        int safePerPage = clamp(perPage, 1, MAX_SEARCH_PAGE_SIZE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/database/search")
                .queryParam("q", q)
                .queryParam("page", safePage)
                .queryParam("per_page", safePerPage);

        if (type != null && !type.isBlank()) {
            builder.queryParam("type", type);
        }

        DiscogsSearchResultsDTO dto =
                discogsRestTemplate.getForObject(builder.toUriString(), DiscogsSearchResultsDTO.class);

        return dto != null ? dto.results() : List.of();
    }

    @Override
    public List<DiscogsDTO> trendingReleases(String sortBy, int count) {
        int safeCount = clamp(count, 1, MAX_TRENDING_COUNT);

        String url = UriComponentsBuilder.fromPath("/database/search")
                .queryParam("type", "release")
                .queryParam("format", "Vinyl")
                .queryParam("sort", sortBy)
                .queryParam("per_page", safeCount)
                .toUriString();

        DiscogsSearchResultsDTO search =
                discogsRestTemplate.getForObject(url, DiscogsSearchResultsDTO.class);

        if (search == null) {
            return List.of();
        }

        // Use images already returned by the search payload to avoid N+1 Discogs calls.
        return search.results()
                .stream()
                .map(release -> new DiscogsDTO(
                        release.id(),
                        release.title(),
                        release.coverImage() != null && !release.coverImage().isBlank()
                                ? release.coverImage()
                                : release.thumb(),
                        release.year(),
                        release.country()
                ))
                .toList();
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
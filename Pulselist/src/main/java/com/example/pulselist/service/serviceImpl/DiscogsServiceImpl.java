package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.DiscogsDTO;
import com.example.pulselist.domains.dto.DiscogsReleaseDTO;
import com.example.pulselist.domains.dto.DiscogsSearchDTO;
import com.example.pulselist.domains.dto.DiscogsSearchResultsDTO;
import com.example.pulselist.service.services.DiscogsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class DiscogsServiceImpl implements DiscogsService {


    public final RestTemplate discogsRestTemplate;

    public DiscogsServiceImpl(RestTemplate discogsRestTemplate){
        this.discogsRestTemplate = discogsRestTemplate;
    }

    @Override
    public List<DiscogsSearchDTO> searchCatalog(String q, String type) {

        String url = UriComponentsBuilder.fromPath("/database/search")
                .queryParam("q", q)
                .queryParam("type", type)
                .toUriString();

        DiscogsSearchResultsDTO dto =
                discogsRestTemplate.getForObject(url, DiscogsSearchResultsDTO.class);

        return dto != null ? dto.results() : List.of();
    }

    // Constructs endpoint to retrieve releases from discogs
    @Override
    public List<DiscogsDTO> trendingReleases(String sortBy, int count) {


        String url = UriComponentsBuilder.fromPath("/database/search")
                .queryParam("type", "release")
                .queryParam("format", "Vinyl")
                .queryParam("sort", sortBy)
                .queryParam("per_page", count)
                .toUriString();

        DiscogsSearchResultsDTO search =
                discogsRestTemplate.getForObject(url, DiscogsSearchResultsDTO.class);

        // Returns
        if (search == null) {
            return List.of();
        }

        // Returns a list of our tending releases
        return search.results()
                .stream()
                .map(release -> {

                    DiscogsReleaseDTO details =
                            discogsRestTemplate.getForObject(
                                    "/releases/" + release.id(),
                                    DiscogsReleaseDTO.class
                            );

                    String image = "";

                    if (details != null &&
                            details.images() != null &&
                            !details.images().isEmpty()) {

                        image = details.images().get(0).uri();
                    }

                    return new DiscogsDTO(
                            release.id(),
                            release.title(),
                            image,
                            release.year(),
                            release.country()
                    );
                })
                .toList();
    }
}

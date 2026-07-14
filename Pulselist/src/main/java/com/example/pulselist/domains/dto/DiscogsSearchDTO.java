package com.example.pulselist.domains.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscogsSearchDTO(Long id,
                               String title,
                               @JsonProperty("cover_image")
                                    String coverImage,
                               @JsonProperty("thumb")
                                    String thumb,
                               String year,
                               String country) {
}

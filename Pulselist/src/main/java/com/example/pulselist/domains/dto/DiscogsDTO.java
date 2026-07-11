package com.example.pulselist.domains.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Discogs record that returns details of music object
public record DiscogsDTO(                Long id,
                                         String title,
                                         String image,
                                         String year,
                                         String country) {
}

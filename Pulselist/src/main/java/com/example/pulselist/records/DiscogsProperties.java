package com.example.pulselist.records;

import org.springframework.boot.context.properties.ConfigurationProperties;

// Defines the properties needed to interact with discogs API
@ConfigurationProperties(prefix = "discogs.api")
public record DiscogsProperties(    String baseUrl,
                                    String token,
                                    String userAgent) {
}

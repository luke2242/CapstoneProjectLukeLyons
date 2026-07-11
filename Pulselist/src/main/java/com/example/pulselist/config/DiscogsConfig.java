package com.example.pulselist.config;

import com.example.pulselist.records.DiscogsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.restclient.RestTemplateBuilder;

@Configuration
@EnableConfigurationProperties(DiscogsProperties.class)
public class DiscogsConfig {

    @Bean
    public RestTemplate discogsRestTemplate(RestTemplateBuilder builder, DiscogsProperties properties) {
        return builder
                .rootUri(properties.baseUrl())
                .defaultHeader("User-Agent", properties.userAgent())
                .defaultHeader("Authorization", "Discogs " + properties.token())
                .build();
    }
}

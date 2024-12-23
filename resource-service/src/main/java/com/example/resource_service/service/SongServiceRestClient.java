package com.example.resource_service.service;

import com.example.resource_service.model.CreateSongRequestDto;
import com.example.resource_service.model.CreateSongResponseDto;
import com.example.resource_service.model.RemoveSongMetadataResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class SongServiceRestClient {

    public static final String SERVICE_NAME = "song-service";
    private final DiscoveryClient discoveryClient;

    @Autowired
    public SongServiceRestClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public CreateSongResponseDto saveSongMetadata(CreateSongRequestDto requestDto) {
        RestClient restClient = RestClient.create();
        Map<String, String> metadataMap = requestDto.metadataMap();
        ServiceInstance serviceInstance = discoveryClient.getInstances(SERVICE_NAME).get(0);
        return restClient.post()
                .uri(serviceInstance.getUri() + "/songs")
                .body(metadataMap)
                .retrieve()
                .body(CreateSongResponseDto.class);
    }

    public RemoveSongMetadataResponseDto removeSongMetadata(String id) {
        RestClient restClient = RestClient.create();
        ServiceInstance serviceInstance = discoveryClient.getInstances(SERVICE_NAME).get(0);
        return restClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .scheme(serviceInstance.getUri().getScheme())
                        .host(serviceInstance.getUri().getHost())
                        .port(serviceInstance.getUri().getPort())
                        .path("/songs")
                        .queryParam("id", id) // Add the request parameter 'id'
                        .build())
                .retrieve()
                .body(RemoveSongMetadataResponseDto.class);
    }
}

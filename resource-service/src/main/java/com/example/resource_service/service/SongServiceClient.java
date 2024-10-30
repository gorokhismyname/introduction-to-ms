package com.example.resource_service.service;

import com.example.resource_service.model.CreateSongRequestDto;
import com.example.resource_service.model.CreateSongResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${feign.name}", url = "${feign.url}")
public interface SongServiceClient {

    @PostMapping(value = "/songs")
    ResponseEntity<CreateSongResponseDto> saveSongMetadata(CreateSongRequestDto requestDto);
}

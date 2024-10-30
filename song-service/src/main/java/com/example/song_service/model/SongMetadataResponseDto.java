package com.example.song_service.model;

import java.util.Map;

public record SongMetadataResponseDto(
        Map<String, String> metadata
) {
}

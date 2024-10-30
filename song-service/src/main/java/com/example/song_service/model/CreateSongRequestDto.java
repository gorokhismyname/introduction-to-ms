package com.example.song_service.model;

import java.util.Map;

public record CreateSongRequestDto(
        Map<String, String> metadata
) {
}

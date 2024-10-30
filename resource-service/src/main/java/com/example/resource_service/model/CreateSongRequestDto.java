package com.example.resource_service.model;

import java.util.Map;

public record CreateSongRequestDto(
        Map<String, String> metadata
) {
}

package com.example.resource_service.model;

import org.apache.tika.metadata.Metadata;

public record CreateSongRequestDto(
        int resourceId,
        Metadata metadata
) {
}

package com.example.song_service.model;

import java.util.List;

public record RemoveSongMetadataResponseDto(
        List<Integer> ids
) {
}

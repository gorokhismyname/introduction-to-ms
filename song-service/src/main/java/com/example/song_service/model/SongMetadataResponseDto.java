package com.example.song_service.model;


public record SongMetadataResponseDto(
        Integer id,
        String artist,
        String album,
        String duration,
        int resourceId,
        String year,
        String title
) {
}

package com.example.song_service.exception;

import lombok.Getter;

@Getter
public class SongMetadataNotFoundException extends RuntimeException {
    private int id;
    public SongMetadataNotFoundException(Integer id) {
        this.id = id;
    }
}

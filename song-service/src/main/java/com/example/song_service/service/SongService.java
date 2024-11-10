package com.example.song_service.service;

import com.example.song_service.model.RemoveSongMetadataResponseDto;
import com.example.song_service.model.SongMetadataResponseDto;
import com.example.song_service.model.CreateSongResponseDto;

import java.util.Map;

public interface SongService {

    CreateSongResponseDto saveSongMetadata(Map<String, String> requestDto);

    SongMetadataResponseDto getSongMetadata(Integer id);

    RemoveSongMetadataResponseDto removeSongMetadata(String id);

}
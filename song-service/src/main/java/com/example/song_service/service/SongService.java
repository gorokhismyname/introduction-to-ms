package com.example.song_service.service;

import com.example.song_service.model.CreateSongRequestDto;
import com.example.song_service.model.RemoveSongMetadataResponseDto;
import com.example.song_service.model.SongMetadataResponseDto;
import com.example.song_service.model.CreateSongResponseDto;

public interface SongService {

    CreateSongResponseDto saveSongMetadata(CreateSongRequestDto requestDto);

    SongMetadataResponseDto getSongMetadata(Integer id);

    RemoveSongMetadataResponseDto removeSongMetadata(String id);

}
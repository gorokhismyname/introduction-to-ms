package com.example.song_service.service;

import com.example.song_service.exception.MetadataFieldValidationException;
import com.example.song_service.model.CreateSongRequestDto;
import com.example.song_service.model.CreateSongResponseDto;
import com.example.song_service.model.SongModel;
import com.example.song_service.repo.SongRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class SongServiceImplSaveTest {

    @Mock
    SongRepo songRepo;

    @InjectMocks
    SongServiceImpl songService;

    @Test
    void shouldReturnCorrectIdWhenCalled() {
        //Given
        int id = 12;

        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("name", "We are the champions");
        metadataMap.put("artist", "Queen");
        metadataMap.put("album", "News of the world");
        metadataMap.put("length", "2:59");
        metadataMap.put("resourceId", "123");
        metadataMap.put("year", "1977");

        SongModel songModel = SongModel.builder().id(id).build();
        CreateSongRequestDto createSongRequestDto = new CreateSongRequestDto(metadataMap);

        //When
        when(songRepo.save(any())).thenReturn(songModel);
        CreateSongResponseDto createSongResponseDto = songService.saveSongMetadata(createSongRequestDto);

        //Then
        assertEquals(id, createSongResponseDto.id());
    }

    @Test
    void shouldThrowValidationExceptionWhenMetadataIsNotValid() {
        //Given
        int id = 12;

        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("name", "We are the champions");
        metadataMap.put("artist", "Queen");
        metadataMap.put("album", "News of the world");
        metadataMap.put("length", "2:59");
        metadataMap.put("resourceId", "123");
//        metadataMap.put("year", "1977");   --> absent field

        SongModel songModel = SongModel.builder().id(id).build();
        CreateSongRequestDto createSongRequestDto = new CreateSongRequestDto(metadataMap);

        //When
        Mockito.lenient().when(songRepo.save(any())).thenReturn(songModel);

        //Then
        assertThrows(MetadataFieldValidationException.class, () -> songService.saveSongMetadata(createSongRequestDto));

    }
}
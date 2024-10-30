package com.example.song_service.service;

import com.example.song_service.exception.SongMetadataNotFoundException;
import com.example.song_service.model.SongMetadataResponseDto;
import com.example.song_service.model.SongModel;
import com.example.song_service.repo.SongRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class SongServiceImplGetTest {

    @Mock
    SongRepo songRepo;

    @InjectMocks
    SongServiceImpl songService;

    @Test
    void shouldReturnCorrectMetadataWhenCalled() {
        //Given
        int id = 12;

        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("name", "We are the champions");
        metadataMap.put("artist", "Queen");
        metadataMap.put("album", "News of the world");
        metadataMap.put("length", "2:59");
        metadataMap.put("resourceId", "123");
        metadataMap.put("year", "1977");

        SongModel songModel = SongModel.builder()
                .id(id)
                .name(metadataMap.get("name"))
                .artist(metadataMap.get("artist"))
                .album(metadataMap.get("album"))
                .length(metadataMap.get("length"))
                .resourceId(metadataMap.get("resourceId"))
                .year(metadataMap.get("year"))
                .build();


        //When
        when(songRepo.findById(any())).thenReturn(Optional.of(songModel));
        SongMetadataResponseDto responseDto = songService.getSongMetadata(id);

        //Then
        assertEquals(metadataMap, responseDto.metadata());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenMetadataNotFound() {
        //Given
        int id = 12;

        //When
        when(songRepo.findById(any())).thenReturn(Optional.empty());

        //Then
        assertThrows(SongMetadataNotFoundException.class, () -> songService.getSongMetadata(id));
    }
}
package com.example.song_service.service;

import com.example.song_service.model.CreateSongResponseDto;
import com.example.song_service.model.SongModel;
import com.example.song_service.repo.SongRepo;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class SongServiceImplSaveTest {

    @Mock
    SongRepo songRepo;
    @Mock
    SongMapper songMapper;

    @InjectMocks
    SongServiceImpl songService;

    @Test
    void shouldReturnCorrectIdWhenCalled() {
        //Given
        int songId = 13;
        SongModel songModel = SongModel.builder().id(songId).build();

        //When
        when(songRepo.save(any())).thenReturn(songModel);
        CreateSongResponseDto createSongResponseDto = songService.saveSongMetadata(Map.of());

        //Then
        assertEquals(songId, createSongResponseDto.id());
    }

    @Test
    void shouldThrowValidationExceptionWhenMetadataIsNotValid() {
        //Given
        //When
        when(songMapper.toModel(any())).thenReturn(SongModel.builder().build());
        Mockito.lenient().when(songRepo.save(any())).thenThrow(new ConstraintViolationException("test", Set.of()));

        //Then
        assertThrows(ConstraintViolationException.class, () -> songService.saveSongMetadata(Map.of()));
    }
}
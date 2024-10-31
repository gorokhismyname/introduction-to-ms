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
    @Mock
    SongMapper songMapper;

    @InjectMocks
    SongServiceImpl songService;

    @Test
    void shouldReturnCorrectMetadataWhenCalled() {
        //Given
        int id = 12;

        SongModel songModel = SongModel.builder()
                .id(1)
                .artist("John Lennon")
                .album("Imagine")
                .duration("3:01")
                .resourceId(101)
                .year("1971")
                .title("Imagine")
                .build();

        SongMetadataResponseDto expectedSongDto = new SongMetadataResponseDto(
                1,
                "John Lennon",
                "Imagine",
                "3:01",
                101,
                "1971",
                "Imagine"
        );

        //When
        when(songRepo.findById(any())).thenReturn(Optional.of(songModel));
        when(songMapper.toDto(any())).thenReturn(expectedSongDto);
        SongMetadataResponseDto actualResponseDto = songService.getSongMetadata(id);

        //Then
        assertEquals(expectedSongDto, actualResponseDto);
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
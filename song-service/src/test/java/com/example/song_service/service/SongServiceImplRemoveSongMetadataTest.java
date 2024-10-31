package com.example.song_service.service;

import com.example.song_service.exception.InvalidCSVFormatException;
import com.example.song_service.model.RemoveSongMetadataResponseDto;
import com.example.song_service.repo.SongRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class SongServiceImplRemoveSongMetadataTest {

    @Mock
    SongRepo songRepo;

    @InjectMocks
    SongServiceImpl songService;

    @Test
    void shouldReturnCorrectArrayOfIdsWhenCalledWithValidInput() {
        //Given
        String id = "12,13";
        List<Integer> idList = List.of(12, 13);

        //When
        doNothing().when(songRepo).deleteAllById(idList);
        RemoveSongMetadataResponseDto removeSongMetadataResponseDto = songService.removeSongMetadata(id);

        //Then
        assertEquals(idList, removeSongMetadataResponseDto.ids());
    }

    @Test
    void shouldThrowInvalidCSVFormatExceptionWhenCalledWithIllegalArgument() {
        //Given
        String id = "12/13";
        List<Integer> idList = List.of(12, 13);

        //When
        Mockito.lenient().doNothing().when(songRepo).deleteAllById(idList);

        //Then
        assertThrows(InvalidCSVFormatException.class, () -> songService.removeSongMetadata(id));
    }
}
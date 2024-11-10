package com.example.resource_service.service;

import com.example.resource_service.exception.InvalidCSVFormatException;
import com.example.resource_service.model.RemoveResourceResponseDto;
import com.example.resource_service.model.RemoveSongMetadataResponseDto;
import com.example.resource_service.model.ResourceModel;
import com.example.resource_service.repo.ResourceRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class ResourceServiceRemoveResourceTest {

    @Mock
    ResourceRepo resourceRepo;
    @Mock
    SongServiceRestClient songServiceRestClient;

    @InjectMocks
    ResourceServiceImpl resourceService;

    @Test
    void shouldReturnCorrectArrayOfIdsWhenCalledWithValidInput() {
        //Given
        String id = "12,13";
        List<Integer> idList = List.of(12, 13);

        //When
        when(resourceRepo.findById(anyInt())).thenReturn(Optional.of(new ResourceModel()));
        doNothing().when(resourceRepo).deleteAllById(idList);
        when(songServiceRestClient.removeSongMetadata(any())).thenReturn(new RemoveSongMetadataResponseDto(List.of()));
        RemoveResourceResponseDto removeResourceResponseDto = resourceService.removeResource(id);

        //Then
        assertEquals(idList, removeResourceResponseDto.ids());
    }

    @Test
    void shouldThrowInvalidCSVFormatExceptionWhenCalledWithIllegalArgument() {
        //Given
        String id = "12/13";
        List<Integer> idList = List.of(12, 13);

        //When
        Mockito.lenient().doNothing().when(resourceRepo).deleteAllById(idList);

        //Then
        assertThrows(InvalidCSVFormatException.class, () -> resourceService.removeResource(id));
    }
}
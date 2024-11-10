package com.example.resource_service.service;

import com.example.resource_service.exception.AudioResourceNotFoundException;
import com.example.resource_service.model.ResourceBinaryDataResponseDto;
import com.example.resource_service.model.ResourceModel;
import com.example.resource_service.repo.ResourceRepo;
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
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class ResourceServiceGetResourceTest {

    @Mock
    ResourceRepo resourceRepo;

    @InjectMocks
    ResourceServiceImpl resourceService;

    @Test
    void shouldReturnValidByteArrayWhenCalled() {
        //Given
        final int id = 12;
        final byte[] audioBytes = {123, 123, 123};
        ResourceModel resourceModel = ResourceModel.builder().mp3File(audioBytes).build();
        ResourceBinaryDataResponseDto responseDto = new ResourceBinaryDataResponseDto(audioBytes);

        //When
        when(resourceRepo.findById(id)).thenReturn(Optional.of(resourceModel));
        byte[] resourceBinaryData = resourceService.getResourceBinaryData(id);

        //Then
        assertEquals(responseDto.audioBytes(), audioBytes);

    }

    @Test
    void shouldThrowNotFoundExceptionWhenCalledWithInvalidId() {
        //Given
        final int id = 13;

        //When
        when(resourceRepo.findById(id)).thenReturn(Optional.empty());

        //Then
        assertThrows(AudioResourceNotFoundException.class, () -> resourceService.getResourceBinaryData(id));
    }
}
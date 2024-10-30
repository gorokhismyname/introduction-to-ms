package com.example.resource_service.service;

import com.example.resource_service.exception.FileProcessingException;
import com.example.resource_service.model.CreateSongResponseDto;
import com.example.resource_service.model.ResourceModel;
import com.example.resource_service.model.UploadResourceResponseDto;
import com.example.resource_service.repo.ResourceRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.tika.metadata.Metadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class ResourceServiceUploadTest {

    @Mock
    SongServiceClient songServiceClient;
    @Mock
    FileProcessor fileProcessor;
    @Mock
    ResourceRepo resourceRepo;

    @InjectMocks
    ResourceServiceImpl resourceService;

    @Test
    void shouldReturnIdOfCreatedEntryToResourceTable() {
        //Given
        int id = 12;
        byte[] content = {123, 123, 123};

        //When
        when(resourceRepo.save(any())).thenReturn(ResourceModel.builder().mp3File(content).id(id).build());
        when(fileProcessor.extractMetadata(content)).thenReturn(new Metadata());
        when(songServiceClient.saveSongMetadata(any())).thenReturn(ResponseEntity.ok(new CreateSongResponseDto(1)));
        UploadResourceResponseDto uploadResourceResponseDto = resourceService.uploadResource(content);

        //Then
        assertEquals(uploadResourceResponseDto.id(), id);
    }

    @Test
    void shouldCallSongServiceOnceWithCorrectInput() {
        //Given
        int id = 12;
        byte[] content = {123, 123, 123};

        //When
        doNothing().when(fileProcessor).validateDatatype(content);
        when(resourceRepo.save(any())).thenReturn(ResourceModel.builder().mp3File(content).id(id).build());
        when(fileProcessor.extractMetadata(content)).thenReturn(new Metadata());
        resourceService.uploadResource(content);

        //Then
        verify(songServiceClient, times(1)).saveSongMetadata(any());
    }

    @Test
    void shouldThrowValidationExceptionWhenInvalidMp3() {
        //Given
        byte[] content = {123, 123, 123};

        //When
        doThrow(new FileProcessingException("test")).when(fileProcessor).validateDatatype(content);

        //Then
        assertThrows(FileProcessingException.class, () -> resourceService.uploadResource(content));
    }
}
package com.example.resource_service.service;

import com.example.resource_service.exception.MultipartFileProcessingException;
import com.example.resource_service.model.CreateSongResponseDto;
import com.example.resource_service.model.ResourceModel;
import com.example.resource_service.model.UploadResourceResponseDto;
import com.example.resource_service.repo.ResourceRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
        MultipartFile file = new MockMultipartFile("test", content);

        //When
        when(resourceRepo.save(any())).thenReturn(ResourceModel.builder().mp3File(content).id(id).build());
        when(fileProcessor.extractMetadata(file)).thenReturn(Map.of());
        when(songServiceClient.saveSongMetadata(any())).thenReturn(ResponseEntity.ok(new CreateSongResponseDto(1)));
        UploadResourceResponseDto uploadResourceResponseDto = resourceService.uploadResource(file);

        //Then
        assertEquals(uploadResourceResponseDto.id(), id);
    }

    @Test
    void shouldCallSongServiceOnceWithCorrectInput() {
        //Given
        int id = 12;
        byte[] content = {123, 123, 123};
        MultipartFile file = new MockMultipartFile("test", content);

        //When
        doNothing().when(fileProcessor).validateFile(file);
        when(resourceRepo.save(any())).thenReturn(ResourceModel.builder().mp3File(content).id(id).build());
        when(fileProcessor.extractMetadata(file)).thenReturn(Map.of());
        resourceService.uploadResource(file);

        //Then
        verify(songServiceClient, times(1)).saveSongMetadata(any());
    }

    @Test
    void shouldThrowValidationExceptionWhenInvalidMp3() {
        //Given
        byte[] content = {123, 123, 123};
        MultipartFile file = new MockMultipartFile("test", content);

        //When
        doThrow(new MultipartFileProcessingException()).when(fileProcessor).validateFile(file);

        //Then
        assertThrows(MultipartFileProcessingException.class, () -> resourceService.uploadResource(file));
    }
}
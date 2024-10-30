package com.example.resource_service.service;

import com.example.resource_service.exception.AudioResourceNotFoundException;
import com.example.resource_service.model.*;
import com.example.resource_service.repo.ResourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepo resourceRepo;
    private final FileProcessor fileProcessor;
    private final SongServiceClient songServiceClient;

    @Autowired
    public ResourceServiceImpl(ResourceRepo resourceRepo, FileProcessor fileProcessor, SongServiceClient songServiceClient) {
        this.resourceRepo = resourceRepo;
        this.fileProcessor = fileProcessor;
        this.songServiceClient = songServiceClient;
    }

    @Override
    public UploadResourceResponseDto uploadResource(MultipartFile file) {

//        fileProcessor.validateFile(file);

        try {
            byte[] bytes = file.getInputStream().readAllBytes();
            handleMetadata(file);

            ResourceModel resourceModel = ResourceModel.builder()
                    .mp3File(bytes)
                    .build();

            ResourceModel savedResourceModel = resourceRepo.save(resourceModel);
            return new UploadResourceResponseDto(savedResourceModel.getId());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceBinaryDataResponseDto getResourceBinaryData(Integer id) {

        ResourceModel resourceModel = resourceRepo.findById(id)
                .orElseThrow(() -> new AudioResourceNotFoundException(id));

        return new ResourceBinaryDataResponseDto(resourceModel.getMp3File());
    }

    @Override
    public RemoveResourceResponseDto removeResource(String id) {
        try {
            List<Integer> idList = Arrays.stream(id.split(","))
                    .map(Integer::parseInt)
                    .toList();
            resourceRepo.deleteAllById(idList);
            return new RemoveResourceResponseDto(idList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleMetadata(MultipartFile file) {
        Map<String, String> metadata = fileProcessor.extractMetadata(file);
        songServiceClient.saveSongMetadata(new CreateSongRequestDto(metadata));
    }
}

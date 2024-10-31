package com.example.resource_service.service;

import com.example.resource_service.exception.AudioResourceNotFoundException;
import com.example.resource_service.exception.InvalidCSVFormatException;
import com.example.resource_service.model.*;
import com.example.resource_service.repo.ResourceRepo;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
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
    public UploadResourceResponseDto uploadResource(byte[] audioData) {
        fileProcessor.validateDatatype(audioData);
        ResourceModel resourceModel = ResourceModel.builder()
                .mp3File(audioData)
                .build();

        ResourceModel savedResourceModel = resourceRepo.save(resourceModel);
        handleMetadata(savedResourceModel.getId(), audioData);
        return new UploadResourceResponseDto(savedResourceModel.getId());
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
            throw new InvalidCSVFormatException(e.getMessage());
        }
    }

    private void handleMetadata(Integer id, byte[] file) {
        Metadata metadata = fileProcessor.extractMetadata(file);
        metadata.add("resourceId", String.valueOf(id));
        Map<String, String> metadataMap = metadataToMap(metadata);
        songServiceClient.saveSongMetadata(new CreateSongRequestDto(metadataMap));
    }

    private Map<String, String> metadataToMap(Metadata metadata) {
        Map<String, String> metadataMap = new HashMap<>();
        for (String name : metadata.names()) {
            metadataMap.put(name, metadata.get(name));
        }
        return metadataMap;
    }

}

package com.example.resource_service.service;

import com.example.resource_service.model.RemoveResourceResponseDto;
import com.example.resource_service.model.ResourceBinaryDataResponseDto;
import com.example.resource_service.model.UploadResourceResponseDto;

public interface ResourceService {

    UploadResourceResponseDto uploadResource(byte[] audioData);

    ResourceBinaryDataResponseDto getResourceBinaryData(Integer id);

    RemoveResourceResponseDto removeResource(String id);

}
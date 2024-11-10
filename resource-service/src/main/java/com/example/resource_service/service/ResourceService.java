package com.example.resource_service.service;

import com.example.resource_service.model.RemoveResourceResponseDto;
import com.example.resource_service.model.UploadResourceResponseDto;

public interface ResourceService {

    UploadResourceResponseDto uploadResource(byte[] audioData);

    byte[] getResourceBinaryData(Integer id);

    RemoveResourceResponseDto removeResource(String id);

}
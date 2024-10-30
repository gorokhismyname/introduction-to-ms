package com.example.resource_service.service;

import com.example.resource_service.model.RemoveResourceResponseDto;
import com.example.resource_service.model.ResourceBinaryDataResponseDto;
import com.example.resource_service.model.UploadResourceResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {

    UploadResourceResponseDto uploadResource(MultipartFile file);

    ResourceBinaryDataResponseDto getResourceBinaryData(Integer id);

    RemoveResourceResponseDto removeResource(String id);

}
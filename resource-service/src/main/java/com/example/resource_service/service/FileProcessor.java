package com.example.resource_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileProcessor {
    void validateFile(MultipartFile file);
    Map<String, String> extractMetadata(MultipartFile file);
}

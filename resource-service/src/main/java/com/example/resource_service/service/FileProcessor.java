package com.example.resource_service.service;

import org.apache.tika.metadata.Metadata;

public interface FileProcessor {
    void validateDatatype(byte[] file);
    Metadata extractMetadata(byte[] file);
}

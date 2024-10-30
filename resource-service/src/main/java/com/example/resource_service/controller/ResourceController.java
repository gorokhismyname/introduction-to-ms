package com.example.resource_service.controller;

import com.example.resource_service.model.RemoveResourceResponseDto;
import com.example.resource_service.model.ResourceBinaryDataResponseDto;
import com.example.resource_service.model.UploadResourceResponseDto;
import com.example.resource_service.service.ResourceService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/resources")
@Validated
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }


    @PostMapping
    public ResponseEntity<UploadResourceResponseDto> uploadResource(
            @RequestParam("file") MultipartFile file
    ) {
        UploadResourceResponseDto responseDto = resourceService.uploadResource(file);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResourceBinaryDataResponseDto> getResourceBinaryData(
            @PathVariable Integer id
    ) {
        ResourceBinaryDataResponseDto responseDto = resourceService.getResourceBinaryData(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<RemoveResourceResponseDto> removeResource(
            @RequestParam("id") @Length(max = 199) String id
    ) {
        RemoveResourceResponseDto responseDto = resourceService.removeResource(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


}

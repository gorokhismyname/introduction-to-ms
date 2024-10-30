package com.example.resource_service.model;

import java.util.List;

public record RemoveResourceResponseDto(
        List<Integer> ids
) {
}

package com.example.resource_service.repo;

import com.example.resource_service.model.ResourceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepo extends JpaRepository<ResourceModel, Integer> {
}

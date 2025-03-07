package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.repository.ToolsRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ToolsService {
    final ToolsRepository repository;

    public ToolsService(ToolsRepository repository) {
        this.repository = repository;
    }

    public Group addGroup(Group group) {
        return repository.save(group);
    }

    public void deleteGroupById(UUID id) {
        repository.deleteById(id);
    }
}

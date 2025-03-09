package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToolsRepository extends JpaRepository<Group, UUID> {
    Group getReferenceByName(String groupName);
    void deleteByName(String name);

    Group findByName(String groupName);

    List<Group> findByNameContainingIgnoreCase(String name);
}

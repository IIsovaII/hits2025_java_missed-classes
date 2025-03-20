package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ToolsRepository extends JpaRepository<Group, String> {
    Group getReferenceByName(String groupName);
    void deleteByName(String name);

    Optional<Group> findByName(String groupName);

    List<Group> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);

    List<Group> findByNameIgnoreCaseStartingWith(String prefix);
}
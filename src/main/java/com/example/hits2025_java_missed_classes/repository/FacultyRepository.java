package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FacultyRepository extends JpaRepository<FacultyEntity, UUID> {
    Optional<FacultyEntity> findByName(String name);
}

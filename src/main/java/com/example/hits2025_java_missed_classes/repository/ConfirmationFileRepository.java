package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.ConfirmationFile;
import com.example.hits2025_java_missed_classes.model.MissRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConfirmationFileRepository extends JpaRepository<ConfirmationFile, UUID>{
    Optional<MissRequest> getMissRequestById(UUID id);

}
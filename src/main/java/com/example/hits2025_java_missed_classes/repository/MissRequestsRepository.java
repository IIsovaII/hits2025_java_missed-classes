package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.MissRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;

public interface MissRequestsRepository extends
        JpaRepository<MissRequest, UUID>,
        JpaSpecificationExecutor<MissRequest> {
}
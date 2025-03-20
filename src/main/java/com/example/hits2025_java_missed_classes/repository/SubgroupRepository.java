package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.Subgroup;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubgroupRepository extends JpaRepository<Subgroup, UUID> {
    Optional<Subgroup> findByGroupNameAndName(@NotBlank String groupName, @NotBlank String groupName1);
}

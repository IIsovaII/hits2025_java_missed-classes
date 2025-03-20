package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.Subgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Subgroup, UUID>{
    Optional<Object> findByName(String name);
    boolean existsByName(String name);
}

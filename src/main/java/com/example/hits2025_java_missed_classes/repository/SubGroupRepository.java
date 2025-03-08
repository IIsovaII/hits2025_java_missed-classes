package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.SubGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubGroupRepository extends JpaRepository<SubGroup, UUID> {

    void deleteByName(String name);
}

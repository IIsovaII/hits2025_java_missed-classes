package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolsRepository extends JpaRepository<Group, Long> {

}

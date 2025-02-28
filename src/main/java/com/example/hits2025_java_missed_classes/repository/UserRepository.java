package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
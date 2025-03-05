package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.JwtBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklistEntity, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM JwtBlacklistEntity t WHERE t.expirationTime <= :now")
    void deleteByExpirationTimeLessThanEqual(long now);
}

package com.example.hits2025_java_missed_classes.repository;

import com.example.hits2025_java_missed_classes.model.TokenBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklistEntity, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM TokenBlacklistEntity t WHERE t.expirationTime <= :now")
    void deleteByExpirationTimeLessThanEqual(long now);
}

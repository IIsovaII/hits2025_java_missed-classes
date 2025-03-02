package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "token_blacklist")
public class TokenBlacklistEntity {
    @Id
    private String token;

    private long expirationTime;
}
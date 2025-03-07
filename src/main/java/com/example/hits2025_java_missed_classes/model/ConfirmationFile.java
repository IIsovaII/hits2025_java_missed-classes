package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "confirmation_file")
public class ConfirmationFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Max(value = 256)
    @Column(length = 256)
    private String name;

    @Column(updatable = false)
    private LocalDateTime attachDate;

    @Lob
    @NotBlank
    @Max(value = 10 * 1024 * 1024, message = "File size must be less than 10 MB")
    private byte[] data;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "miss_request_id")
    MissRequest missRequest;

    @PrePersist
    protected void onCreate() {
        attachDate = LocalDateTime.now();
    }
}
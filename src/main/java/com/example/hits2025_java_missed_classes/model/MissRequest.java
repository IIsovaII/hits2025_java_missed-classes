package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "miss_request")
@NoArgsConstructor
public class MissRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private MissRequestType type;

    @NotNull
    private MissRequestStatus status = MissRequestStatus.IN_QUEUE;

    @OneToMany(mappedBy = "missRequest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ConfirmationFile> confirmationFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deans_worker_id")
    User statusSetBy = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    User creator;
}

package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY) // хорошая практика для производительности. Данные о факультете будут находится только по прямому запросу
    @JoinColumn(name = "faculty_id", nullable = false) // Внешний ключ
    private FacultyEntity faculty;

    @OneToMany(mappedBy = "groups")
    private List<SubGroupEntity> sub_group;
}

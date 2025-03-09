package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "sub_group")
public class Subgroup {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "group_name", nullable = false)
    private Group group;

    @ManyToMany
    private List<User> user;

    private String name;
}

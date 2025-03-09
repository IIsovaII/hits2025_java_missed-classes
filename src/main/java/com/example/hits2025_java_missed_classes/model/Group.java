package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "education_group")
public class Group {
    @Id
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subgroup> subgroups;
}
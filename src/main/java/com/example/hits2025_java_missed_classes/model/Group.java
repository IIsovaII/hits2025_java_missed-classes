package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "group")
public class Group {

    private String groupName;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubGroup> subGroups;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
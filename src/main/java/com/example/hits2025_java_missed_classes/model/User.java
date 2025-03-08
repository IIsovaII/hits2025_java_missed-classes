package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String surname;
    private String patronymic;
    private String password;
    @ManyToMany
    private List<Group> favGroups;
    @ManyToMany
    private List<Group> groups;
    @ManyToMany
    private List<SubGroup> subGroup;
    @ManyToMany
    private List<SubGroup> favSubgroups;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles; // можно List<String>

    @OneToMany(mappedBy = "statusSetBy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MissRequest> reviewedMissRequests = new ArrayList<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MissRequest> createdMissRequests = new ArrayList<>();
}
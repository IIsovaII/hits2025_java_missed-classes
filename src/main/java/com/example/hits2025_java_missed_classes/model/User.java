package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String name;

    @Column(unique = true)
    @NotBlank
    @Email
    private String email;

    @NotNull
    private String surname;

    private String patronymic;

    @NotEmpty
    @Size(min = 5, max = 64)
    private String password;

    @ManyToOne
    @JoinColumn(name = "group_name")
    private Group group;
    @Column(name = "group_name", insertable=false, updatable=false)
    private String groupName;

    @ManyToMany
    private List<Subgroup> subgroups;

    @ManyToMany
    private List<Group> favoriteGroups;

    @ManyToMany
    private List<Subgroup> favoriteSubgroups;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "statusSetBy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MissRequest> reviewedMissRequests = new ArrayList<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MissRequest> createdMissRequests = new ArrayList<>();
}
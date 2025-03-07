package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sub_group")
public class SubGroup {

    @ManyToOne
    @JoinColumn(name = "group", nullable = false)
    private Group group;

    @ManyToMany
    private List<User> user;

    @Id
    private String name;
}

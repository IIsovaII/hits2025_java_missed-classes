package com.example.hits2025_java_missed_classes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sub_group")
public class SubGroup {

    @ManyToOne
    @JoinColumn(name = "group", nullable = false)
    private Group group;

    @Id
    private String name;
}

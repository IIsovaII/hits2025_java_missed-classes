package com.example.hits2025_java_missed_classes.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String surname;
    private String patronymic;
    @Column(unique = true)
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    // TODO: удалить roles из БД
    private Set<String> roles = new HashSet<>();

    public User() {

    }

    public User(String name, String surname, String patronymic, String email, String password, Set<String> roles) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.roles = (roles == null) ? new HashSet<>(Set.of(Role.USER)) : new HashSet<>(roles);
    }

    // Геттер и сеттер пустых ролей
    public Set<String> getRoles() {
        return (roles == null) ? new HashSet<>() : roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = (roles == null) ? new HashSet<>() : roles;
    }
}
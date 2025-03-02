package com.example.hits2025_java_missed_classes.dto;

import java.util.Set;
import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private Set<String> roles;
}

package com.example.hits2025_java_missed_classes.dto;

import com.example.hits2025_java_missed_classes.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDto {
    @NotNull
    private UUID id;

    @NotNull
    private String name;

    private String groupName;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private String surname;

    private String patronymic;

    private Set<Role> roles;
}

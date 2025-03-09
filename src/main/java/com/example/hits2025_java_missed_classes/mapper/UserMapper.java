package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.UserDto;
import com.example.hits2025_java_missed_classes.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User model) {
        return new UserDto(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getSurname(),
                model.getPatronymic(),
                model.getRoles()
        );
    }
}
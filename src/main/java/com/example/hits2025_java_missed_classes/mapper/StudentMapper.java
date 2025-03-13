package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.StudentDto;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import com.example.hits2025_java_missed_classes.model.User;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toDto(User model) {
        return new StudentDto(
                model.getId(),
                model.getName(),
                model.getSurname(),
                model.getPatronymic(),
                model.getGroupName(),
                model.getSubgroups().stream().map(Subgroup::getName).toList()
        );
    }
}
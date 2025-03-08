package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.StudentDto;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.SubGroup;
import com.example.hits2025_java_missed_classes.model.User;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toDto(User model) {
        return new StudentDto(
                model.getId(),
                model.getUsername(),
                model.getSurname(),
                model.getPatronymic(),
                model.getGroupName(),
                model.getSubGroup().stream().map(SubGroup::getName).toList()
        );
    }
}
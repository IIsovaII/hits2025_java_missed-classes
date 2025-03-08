package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.StudentsPagedListDto;
import com.example.hits2025_java_missed_classes.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class StudentsPagedListMapper {
    final StudentMapper studentMapper;
    final PaginationMapper paginationMapper;

    public StudentsPagedListMapper(StudentMapper studentMapper, PaginationMapper paginationMapper) {
        this.studentMapper = studentMapper;
        this.paginationMapper = paginationMapper;
    }

    public StudentsPagedListDto toDto(Page<User> domainPage) {
        return new StudentsPagedListDto(
                paginationMapper.toDTO(domainPage),
                domainPage
                        .getContent()
                        .stream()
                        .map(studentMapper::toDto)
                        .toList()
        );
    }
}

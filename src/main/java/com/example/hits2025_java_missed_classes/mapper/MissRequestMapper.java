package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.MissRequestDto;
import com.example.hits2025_java_missed_classes.model.MissRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MissRequestMapper {
    public final ConfirmationFileMapper confirmationFileMapper;
    private final StudentMapper studentMapper;

    public MissRequestMapper(ConfirmationFileMapper confirmationFileMapper, StudentMapper studentMapper) {
        this.confirmationFileMapper = confirmationFileMapper;
        this.studentMapper = studentMapper;
    }

    @Transactional
    public MissRequestDto toDto(MissRequest model) {
        return new MissRequestDto(
                model.getId(),
                model.getStartDate(),
                model.getEndDate(),
                model.getType(),
                studentMapper.toDto(model.getCreator()),
                model.getStatus(),
                model.getConfirmationFiles()
                        .stream()
                        .map(confirmationFileMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}
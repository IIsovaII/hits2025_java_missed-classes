package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.MissRequestDto;
import com.example.hits2025_java_missed_classes.model.MissRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MissRequestMapper {
    public final ConfirmationFileMapper confirmationFileMapper;

    public MissRequestMapper(ConfirmationFileMapper confirmationFileMapper) {
        this.confirmationFileMapper = confirmationFileMapper;
    }

    @Transactional
    public MissRequestDto toDto(MissRequest model) {
        return new MissRequestDto(
                model.getId(),
                model.getStartDate(),
                model.getEndDate(),
                model.getType(),
                model.getStatus(),
                model.getConfirmationFiles()
                        .stream()
                        .map(confirmationFileMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}
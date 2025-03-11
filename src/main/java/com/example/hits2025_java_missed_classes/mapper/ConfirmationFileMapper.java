package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.ConfirmationFileDto;
import com.example.hits2025_java_missed_classes.model.ConfirmationFile;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationFileMapper {
    public ConfirmationFileDto toDto(ConfirmationFile model) {
        return new ConfirmationFileDto(
                model.getName(),
                model.getAttachDate()
        );
    }
}
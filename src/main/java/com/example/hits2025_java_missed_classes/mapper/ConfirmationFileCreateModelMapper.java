package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.ConfirmationFileCreateModelDto;
import com.example.hits2025_java_missed_classes.model.ConfirmationFile;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationFileCreateModelMapper {
    public ConfirmationFile toDomain(ConfirmationFileCreateModelDto model) {
        ConfirmationFile file = new ConfirmationFile();

        file.setName(model.getName());
        file.setData(model.getData());

        return file;
    }
}
package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.MissRequestCreateModelDto;
import com.example.hits2025_java_missed_classes.model.MissRequestCreateModel;
import org.springframework.stereotype.Component;

@Component
public class MissRequestCreateModelMapper {
    final ConfirmationFileMapper confirmationFileMapper;

    public MissRequestCreateModelMapper(ConfirmationFileMapper confirmationFileMapper) {
        this.confirmationFileMapper = confirmationFileMapper;
    }

    public MissRequestCreateModel toDomain(MissRequestCreateModelDto model) {
        return new MissRequestCreateModel(
                model.getStartDate(),
                model.getEndDate(),
                model.getType(),
                model.getConfirmationFiles().stream().map(confirmationFileMapper::toDomain).toList()
        );
    }
}

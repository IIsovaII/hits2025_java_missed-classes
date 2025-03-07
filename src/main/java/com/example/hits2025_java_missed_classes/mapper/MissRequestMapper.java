package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.MissRequestDto;
import com.example.hits2025_java_missed_classes.dto.MissRequestPagedListDto;
import com.example.hits2025_java_missed_classes.dto.Pagination;
import com.example.hits2025_java_missed_classes.model.MissRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MissRequestMapper {
    public final ConfirmationFileMapper confirmationFileMapper;

    public MissRequestMapper(ConfirmationFileMapper confirmationFileMapper) {
        this.confirmationFileMapper = confirmationFileMapper;
    }

    public MissRequestDto toDto(MissRequest model) {
        return new MissRequestDto(
                model.getId(),
                model.getStartDate(),
                model.getEndDate(),
                model.getType(),
                model.getStatus(),
                model.getConfirmationFiles()
                        .stream()
                        .map(confirmationFileMapper::toDTO)
                        .toList()
        );
    }
}
package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.MissRequestEditModelDto;
import com.example.hits2025_java_missed_classes.model.MissRequestEditModel;
import org.springframework.stereotype.Component;

@Component
public class MissRequestEditModelMapper {
    public MissRequestEditModel toDomain(MissRequestEditModelDto model) {
        return new MissRequestEditModel(
                model.getStartDate(),
                model.getEndDate(),
                model.getType(),
                model.getStatus()
        );
    }
}
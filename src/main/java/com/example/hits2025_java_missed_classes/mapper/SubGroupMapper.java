package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.SubGroup;
import org.springframework.stereotype.Component;

@Component
public class SubGroupMapper {

    public SubGroupDTO toDTO(SubGroup subGroup) {
        return new SubGroupDTO(subGroup.getName());
    }

    public SubGroup toModel(SubGroupDTO subGroupDTO) {
        return new SubGroup();
    }
}

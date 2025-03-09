package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.GroupDto;
import com.example.hits2025_java_missed_classes.dto.SubgroupDto;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import org.springframework.stereotype.Component;

@Component
public class SubgroupMapper {

    public SubgroupDto toDto(Subgroup subgroup) {
        return new SubgroupDto(subgroup.getName());
    }

    public Subgroup toModel(SubgroupDto subgroupDto) {
        return new Subgroup();
    }
}

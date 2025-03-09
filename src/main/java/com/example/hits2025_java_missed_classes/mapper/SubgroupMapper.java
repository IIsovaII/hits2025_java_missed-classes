package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.SubgroupDTO;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import org.springframework.stereotype.Component;

@Component
public class SubgroupMapper {

    public SubgroupDTO toDTO(Subgroup subgroup) {
        return new SubgroupDTO(subgroup.getName());
    }

    public Subgroup toModel(SubgroupDTO subgroupDTO) {
        return new Subgroup();
    }
}

package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GroupMapper {

    @Autowired
    SubgroupMapper subgroupMapper;

    public GroupDTO toDTO(Group group) {
        return new GroupDTO(group.getName()
                ,group.getSubgroups().stream().map(g -> subgroupMapper.toDTO(g)).collect(Collectors.toList()));
    }

    public Group toModel(GroupDTO groupDto) {
        return new Group();
    }
}
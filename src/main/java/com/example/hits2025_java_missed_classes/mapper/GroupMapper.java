package com.example.hits2025_java_missed_classes.mapper;

import com.example.hits2025_java_missed_classes.dto.GroupDto;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GroupMapper {

    @Autowired
    SubgroupMapper subgroupMapper;

    public GroupDto toDto(Group group) {
        return new GroupDto(group.getName()
                ,group.getSubgroups().stream().map(g -> subgroupMapper.toDto(g)).collect(Collectors.toList()));
    }

    public Group toModel(GroupDto groupDto) {
        return new Group();
    }
}
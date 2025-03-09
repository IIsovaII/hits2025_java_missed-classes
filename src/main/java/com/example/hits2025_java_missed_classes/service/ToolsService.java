package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.GroupDto;
import com.example.hits2025_java_missed_classes.dto.SubgroupDto;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubgroupMapper;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.SubgroupRepository;
import com.example.hits2025_java_missed_classes.repository.ToolsRepository;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ToolsService {
    private final ToolsRepository toolsRepository;
    private final SubgroupRepository subgroupRepository;
    private final UserRepository userRepository;

    public ToolsService(ToolsRepository repository, SubgroupRepository subgroupRepository, UserRepository userRepository) {
        this.toolsRepository = repository;
        this.subgroupRepository = subgroupRepository;
        this.userRepository = userRepository;
    }

    public void addStudentToGroup(UUID studentId, String groupName) {
        User user = userRepository.getReferenceById(studentId);
        user.setGroupName(groupName);
        userRepository.save(user);
    }

    public void addStudentToSubgroup(UUID studentId, UUID subgroupId) {
        User user = userRepository.getReferenceById(studentId);
        Subgroup subgroup = subgroupRepository.getReferenceById(subgroupId);
        user.getSubgroup().add(subgroup);
        userRepository.save(user);
    }

    public void addGroup(Group group) {
        toolsRepository.save(group);
    }

    public void addSubgroup(Subgroup subgroup) {
        subgroupRepository.save(subgroup);
    }

    public void deleteGroupByName(String name) {
        toolsRepository.deleteByName(name);
    }

    public void deleteSubgroupByName(UUID subgroupName) {
        subgroupRepository.deleteById(subgroupName);
    }

    public void deleteStudentFromGroup(UUID userId) {
        User user = userRepository.getReferenceById(userId);
        user.setGroupName(null);
        userRepository.save(user);
    }

    public void deleteStudentFromSubgroup(UUID userId, UUID subgroupId) {
        User user = userRepository.getReferenceById(userId);
        Subgroup subgroup = subgroupRepository.getReferenceById(subgroupId);
        user.getSubgroup().remove(subgroup);
        userRepository.save(user);
    }
}

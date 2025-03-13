package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.SubgroupRepository;
import com.example.hits2025_java_missed_classes.repository.ToolsRepository;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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
        user.setGroup(toolsRepository.getReferenceByName(groupName));
        userRepository.save(user);
    }

    public void addStudentToSubgroup(UUID studentId, UUID subgroupId, String groupName) {
        User user = userRepository.getReferenceById(studentId);
        if (Objects.equals(user.getGroup().getName(), groupName)) {
            Subgroup subgroup = subgroupRepository.getReferenceById(subgroupId);
            user.getSubgroups().add(subgroup);
            userRepository.save(user);
        }
    }

    public void addGroup(Group group) {
        group.getSubgroups().forEach(subgroup -> {
            subgroup.setGroup(group);
        });
        toolsRepository.save(group);
    }

    public void addSubgroup(Subgroup subgroup, String groupName) {
        subgroup.setGroup(toolsRepository.findByName(groupName));
        subgroupRepository.save(subgroup);
    }

    @Transactional
    public void deleteGroupByName(String name) {
        toolsRepository.deleteByName(name);
    }

    public void deleteSubgroupByName(UUID subgroupName) {
        subgroupRepository.deleteById(subgroupName);
    }

    public void deleteStudentFromGroup(UUID userId) {
        User user = userRepository.getReferenceById(userId);
        user.setGroup(null);
        user.setSubgroups(null);
        userRepository.save(user);
    }

    public void deleteStudentFromSubgroup(UUID userId, UUID subgroupId) {
        User user = userRepository.getReferenceById(userId);
        Subgroup subgroup = subgroupRepository.getReferenceById(subgroupId);
        user.getSubgroups().remove(subgroup);
        userRepository.save(user);
    }
}

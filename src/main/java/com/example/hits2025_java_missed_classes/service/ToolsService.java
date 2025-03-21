package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.UserDto;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.BadRequestException;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.SubgroupRepository;
import com.example.hits2025_java_missed_classes.repository.ToolsRepository;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Group group = toolsRepository
                .findByName(groupName)
                .orElseThrow(() -> new EntityNotFoundException("Group " + groupName + " not found"));
        user.setGroup(group);
        userRepository.save(user);
    }

    public void addStudentToSubgroup(UUID studentId, UUID subgroupId) {
        User user = userRepository
                .findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("User " + studentId + " not found"));
        Subgroup subgroup = subgroupRepository
                .findById(subgroupId)
                .orElseThrow(() -> new EntityNotFoundException("User " + studentId + " not found"));

        if (user.getSubgroups().contains(subgroup)) {
            throw new BadRequestException("User " + studentId + " is already in subgroup" + subgroupId);
        }

        user.getSubgroups().add(subgroup);
        userRepository.save(user);
    }

    public Group addGroup(String groupName) {
         if (toolsRepository.existsByName(groupName)){
             throw new BadRequestException("Group " + groupName + " already exists");
         }

         Group group = new Group();
         group.setName(groupName);

        return toolsRepository.save(group);
    }

    public Subgroup addSubgroup(String subgroupName, String groupName) {
        Group group = toolsRepository
                .findByName(groupName)
                .orElseThrow(() -> new EntityNotFoundException("Group " + groupName + " not found"));

        Subgroup subgroup = new Subgroup();
        subgroup.setName(subgroupName);
        subgroup.setGroup(group);

        return subgroupRepository.save(subgroup);
    }

    @Transactional
    public void deleteGroupByName(String name) {
        toolsRepository.deleteByName(name);
    }

    public void deleteSubgroupById(UUID subgroupId) {
        subgroupRepository.deleteById(subgroupId);
    }

    public void deleteStudentFromGroup(UUID userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));

        user.setGroup(null);
        user.setSubgroups(null);

        userRepository.save(user);
    }

    public void deleteStudentFromSubgroup(UUID userId, UUID subgroupId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));
        Subgroup subgroup = subgroupRepository
                .findById(subgroupId)
                .orElseThrow(() -> new EntityNotFoundException("Subgroup " + subgroupId + " not found"));
        user.getSubgroups().remove(subgroup);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

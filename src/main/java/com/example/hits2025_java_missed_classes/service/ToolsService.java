package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.mapper.SubGroupMapper;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.SubGroup;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.ToolsRepository;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ToolsService {
    final ToolsRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    SubGroupMapper subGroupMapper;
    public ToolsService(ToolsRepository repository) {
        this.repository = repository;
    }

    public boolean addStudentToGroup(UUID studentId, Role role, UUID groupId, List<SubGroupDTO> subGroups) {
        User user = userRepository.getReferenceById(studentId);
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        user.setGroupId(groupId);
        if(!subGroups.isEmpty()) {
            user.setSubGroupId(subGroups.stream().map(s -> subGroupMapper.toModel(s)).collect(Collectors.toList()));
        }
        userRepository.save(user);
        return true;
    }

    public boolean addRoleById(UUID id, Role role) {
        User user = userRepository.getReferenceById(id);
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    public void addGroup(Group group) {
        repository.save(group);
    }

    public void deleteGroupById(UUID id) {
        repository.deleteById(id);
    }
}

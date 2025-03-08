package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubGroupMapper;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.SubGroup;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.SubGroupRepository;
import com.example.hits2025_java_missed_classes.repository.ToolsRepository;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ToolsService {
    final ToolsRepository toolsRepository;
    final SubGroupRepository subGroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    SubGroupMapper subGroupMapper;
    @Autowired
    GroupMapper groupMapper;
    public ToolsService(ToolsRepository repository, SubGroupRepository subGroupRepository) {
        this.toolsRepository = repository;
        this.subGroupRepository = subGroupRepository;
    }

    public boolean addStudentToGroup(UUID studentId, Role role, List<GroupDTO> groups, List<SubGroupDTO> subGroups) {
        User user = userRepository.getReferenceById(studentId);
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        if(!groups.isEmpty()) {
            user.setGroups(groups.stream().map(s -> groupMapper.toModel(s)).collect(Collectors.toList()));
        }

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
        toolsRepository.save(group);
    }

    public void deleteGroupByName(String name) {
        toolsRepository.deleteByName(name);
    }

    public void deleteSubGroupByName(UUID subGroupName) {
        subGroupRepository.deleteById(subGroupName);
    }
}

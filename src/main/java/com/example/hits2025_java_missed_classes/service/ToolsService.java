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

    public void addStudentToGroup(UUID studentId, Role role, String groupName) {
        User user = userRepository.getReferenceById(studentId);
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        user.setGroupName(groupName);

        userRepository.save(user);
    }

    public void addStudentToSubGroup(UUID studentId, Role role, UUID subGroupsId) {
        User user = userRepository.getReferenceById(studentId);
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);

        SubGroup subGroup = subGroupRepository.getReferenceById(subGroupsId);
        List<SubGroup> subGroups = user.getSubgroups();
        subGroups.add(subGroup);

        userRepository.save(user);
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

    public void addSubGroup(SubGroup subGroup) {
        subGroupRepository.save(subGroup);
    }

    public void deleteGroupByName(String name) {
        toolsRepository.deleteByName(name);
    }

    public void deleteSubGroupByName(UUID subGroupName) {
        subGroupRepository.deleteById(subGroupName);
    }

    public void deleteStudentFromGroup(UUID userId) {
        User user = userRepository.getReferenceById(userId);
        user.setGroupName(null);
        userRepository.save(user);
    }

    public void deleteStudentFromSubGroup(UUID userId, UUID subGroupId) {
        User user = userRepository.getReferenceById(userId);
        SubGroup subGroup = subGroupRepository.getReferenceById(subGroupId);
        user.getSubgroups().remove(subGroup);
        userRepository.save(user);
    }
}

package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubGroupMapper;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.SubGroup;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.SubGroupRepository;
import com.example.hits2025_java_missed_classes.repository.ToolsRepository;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SubGroupRepository subGroupRepository;

    @Autowired
    ToolsRepository toolsRepository;

    @Autowired
    SubGroupMapper subGroupMapper;

    @Autowired
    GroupMapper groupMapper;

    public void addGroupToFav(UUID userId, List<GroupDTO> groups) {

        User user = userRepository.getReferenceById(userId);
        user.setFavGroups(groups.stream().map(s -> groupMapper.toModel(s)).collect(Collectors.toList()));
        userRepository.save(user);
    }

    public void addSubGroupToFav(UUID userId, List<SubGroupDTO> subGroups) {
        User user = userRepository.getReferenceById(userId);
        user.setFavSubgroups(subGroups.stream().map(s -> subGroupMapper.toModel(s)).collect(Collectors.toList()));
        userRepository.save(user);
    }

    public void deleteGroupFromFav(UUID userId, String groupName) {
        User user = userRepository.getReferenceById(userId);
        Group group = toolsRepository.findByName(groupName);
        user.getFavGroups().remove(group);
        userRepository.save(user);
    }

    public void deleteSubGroupFromFav(UUID userId, UUID subGroupId) {
        User user = userRepository.getReferenceById(userId);
        SubGroup subGroup = subGroupRepository.getReferenceById(subGroupId);
        user.getFavSubgroups().remove(subGroup);
        userRepository.save(user);
    }

    public List<GroupDTO> getFavGroups(UUID id) {
        User user = userRepository.getReferenceById(id);
        List<Group> groups = user.getFavGroups();
        return groups.stream().map(g -> groupMapper.toDTO(g)).toList();
    }

    public List<GroupDTO> getGroups(String groupName) {
        List<Group> groups = toolsRepository.findByNameContainingIgnoreCase(groupName);
        return groups.stream().map(g -> groupMapper.toDTO(g)).toList();
    }
}

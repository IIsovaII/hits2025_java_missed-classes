package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.GetSubGroupDto;
import com.example.hits2025_java_missed_classes.dto.GroupDto;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubgroupMapper;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.SubgroupRepository;
import com.example.hits2025_java_missed_classes.repository.ToolsRepository;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    private final UserRepository userRepository;
    private final SubgroupRepository subgroupRepository;
    private final ToolsRepository toolsRepository;
    private final GroupMapper groupMapper;

    public GroupService(UserRepository userRepository, SubgroupRepository subgroupRepository, ToolsRepository toolsRepository, SubgroupMapper subgroupMapper, GroupMapper groupMapper) {
        this.userRepository = userRepository;
        this.subgroupRepository = subgroupRepository;
        this.toolsRepository = toolsRepository;
        this.groupMapper = groupMapper;
    }

    public void addGroupToFav(UUID userId, String groupName) {
        User user = userRepository.getReferenceById(userId);
        user.getFavoriteGroups().add(toolsRepository.getReferenceByName(groupName));
        userRepository.save(user);
    }

    public void addSubgroupToFav(UUID userId, UUID subgroupId) {
        User user = userRepository.getReferenceById(userId);
        user.getFavoriteSubgroups().add(subgroupRepository.getReferenceById(subgroupId));
        userRepository.save(user);
    }

    public void deleteGroupFromFav(UUID userId, String groupName) {
        User user = userRepository.getReferenceById(userId);
        user.getFavoriteGroups().remove(toolsRepository.getReferenceByName(groupName));
        userRepository.save(user);
    }

    public void deleteSubgroupFromFav(UUID userId, UUID subgroupId) {
        User user = userRepository.getReferenceById(userId);
        user.getFavoriteSubgroups().remove( subgroupRepository.getReferenceById(subgroupId));
        userRepository.save(user);
    }

    public List<GroupDto> getFavGroups(UUID id) {
        User user = userRepository.getReferenceById(id);
        List<Group> groups = user.getFavoriteGroups();
        return groups.stream().map(groupMapper::toDto).toList();
    }

    public List<GroupDto> getGroups(String groupName) {
        List<Group> groups = toolsRepository.findByNameContainingIgnoreCase(groupName);
        return groups.stream().map(groupMapper::toDto).toList();
    }

    public UUID getSubgroup(GetSubGroupDto getSubGroupDto) {
        return subgroupRepository.findByGroupNameAndName(getSubGroupDto.getGroupName(),getSubGroupDto.getSubgroupName());
    }
}

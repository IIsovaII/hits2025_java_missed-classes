package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.GetSubGroupDto;
import com.example.hits2025_java_missed_classes.dto.GroupDto;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.BadRequestException;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubgroupMapper;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.GroupRepository;
import com.example.hits2025_java_missed_classes.repository.SubgroupRepository;
import com.example.hits2025_java_missed_classes.repository.ToolsRepository;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    private final UserRepository userRepository;
    private final SubgroupRepository subgroupRepository;
    private final ToolsRepository toolsRepository;
    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;

    public GroupService(UserRepository userRepository, SubgroupRepository subgroupRepository, ToolsRepository toolsRepository, SubgroupMapper subgroupMapper, GroupMapper groupMapper, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.subgroupRepository = subgroupRepository;
        this.toolsRepository = toolsRepository;
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
    }

    public void addGroupToFav(UUID userId, String groupName) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));

        Group group = toolsRepository
                .findByName(groupName)
                .orElseThrow(() -> new EntityNotFoundException("Group " + groupName + " not found"));

        if (user.getFavoriteGroups().contains(group)) {
            throw new BadRequestException("User " + userId + " is already has in favorites group" + groupName);
        }

        user.getFavoriteGroups().add(group);
        userRepository.save(user);
    }

    public void addSubgroupToFav(UUID userId, UUID subgroupId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));

        Subgroup subgroup = groupRepository
                .findById(subgroupId)
                .orElseThrow(() -> new EntityNotFoundException("Subgroup " + subgroupId + " not found"));

        if (user.getFavoriteSubgroups().contains(subgroup)) {
            throw new BadRequestException("User " + userId + " is already has in favorites subgroup" + subgroup.getId());
        }

        user.getFavoriteSubgroups().add(subgroup);
        userRepository.save(user);
    }

    public void deleteGroupFromFav(UUID userId, String groupName) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));

        Group group = toolsRepository
                .findByName(groupName)
                .orElseThrow(() -> new EntityNotFoundException("Group " + groupName + " not found"));

        user.getFavoriteGroups().remove(group);
        userRepository.save(user);
    }

    public void deleteSubgroupFromFav(UUID userId, UUID subgroupId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));

        Subgroup subgroup = groupRepository
                .findById(subgroupId)
                .orElseThrow(() -> new EntityNotFoundException("Subgroup " + subgroupId + " not found"));

        user.getFavoriteSubgroups().remove(subgroup);
        userRepository.save(user);
    }

    public List<GroupDto> getFavGroups(UUID userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));

        List<Group> groups = user.getFavoriteGroups();
        return groups.stream()
                .map(groupMapper::toDto)
                .toList();
    }

    public List<GroupDto> getGroups(String groupName) {
        List<Group> groups = toolsRepository.findByNameIgnoreCaseStartingWith(groupName);
        return groups.stream()
                .map(groupMapper::toDto)
                .toList();
    }

    public UUID getSubgroup(GetSubGroupDto getSubGroupDto) {
        Subgroup subgroup = subgroupRepository.findByGroupNameAndName(
                getSubGroupDto.getGroupName(),
                getSubGroupDto.getSubgroupName()
        ).orElseThrow(() -> new EntityNotFoundException("Subgroup " + getSubGroupDto.getSubgroupName() + " not found"));

        return subgroup.getId();
    }
}

package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubGroupMapper;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SubGroupMapper subGroupMapper;

    @Autowired
    GroupMapper groupMapper;

    public boolean addGroupToFav(UUID userId, List<GroupDTO> groups, List<SubGroupDTO> subGroups) {

        User user = userRepository.getReferenceById(userId);
        if(!groups.isEmpty()) {
            user.setFavGroups(groups.stream().map(s->groupMapper.toModel(s)).collect(Collectors.toList()));
        }
        if(!subGroups.isEmpty()) {
            user.setFavSubGroupId(subGroups.stream().map(s -> subGroupMapper.toModel(s)).collect(Collectors.toList()));
        }
        userRepository.save(user);
        return true;
    }

    public boolean deleteGroupFromFav(UUID userId, List<Group> groups) {
        User user = userRepository.getReferenceById(userId);
        user.setGroupName(groups);
    }
}

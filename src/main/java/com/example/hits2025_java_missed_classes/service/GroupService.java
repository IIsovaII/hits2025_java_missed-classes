package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.mapper.SubGroupMapper;
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

    public boolean addGroupToFav(UUID userId,UUID groupId, List<SubGroupDTO> subGroups) {

        User user = userRepository.getReferenceById(userId);
        if(groupId != null) {
            user.setGroupId(groupId);
        }
        if(!subGroups.isEmpty()) {
            user.setSubGroupId(subGroups.stream().map(s -> subGroupMapper.toModel(s)).collect(Collectors.toList()));
        }
        userRepository.save(user);
        return true;
    }
}

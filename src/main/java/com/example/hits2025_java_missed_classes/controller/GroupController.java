package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.StudentAddRequest;
import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.dto.TeacherAddRequest;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.model.Group;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.SubGroup;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.security.JwtUtil;
import com.example.hits2025_java_missed_classes.service.GroupService;
import com.example.hits2025_java_missed_classes.service.ToolsService;
import com.example.hits2025_java_missed_classes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/group")
@Tag(name = "group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/favourite/group/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addGroup(@RequestBody  List<GroupDTO> groups, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(username);
        groupService.addGroupToFav(user.get().getId(),groups);
        return null;
    }

    @PostMapping("/favourite/subGroup/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addSubGroup(@RequestBody List<SubGroupDTO> subGroups, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(username);

        groupService.addSubGroupToFav(user.get().getId(), subGroups);
        return null;
    }

    @DeleteMapping("/favourite/group/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteGroupFromFav(@RequestBody String groupName, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(username);
        groupService.deleteGroupFromFav(user.get().getId(), groupName);
    }

    @DeleteMapping("/favourite/subGroup/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteSubGroupFromFav(@RequestBody UUID subGroupId, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(username);
        groupService.deleteSubGroupFromFav(user.get().getId(), subGroupId);
    }

    @GetMapping("/favourite")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public List<GroupDTO> getFavouriteGroup(@RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(username);
        return groupService.getFavGroups(user.get().getId());
    }

    @GetMapping("/favourite/searchGroup")
    public List<GroupDTO> getGroupsByName(@RequestParam String groupName) {
        return groupService.getGroups(groupName);
    }
}
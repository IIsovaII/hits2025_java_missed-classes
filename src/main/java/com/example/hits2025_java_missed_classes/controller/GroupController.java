package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.StudentAddRequest;
import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.dto.TeacherAddRequest;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
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
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/favourite/{groupId}/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addGroup(@PathVariable UUID groupId, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(username);
        groupService.addGroupToFav(user.get().getId(),groupId, null);
        return null;
    }

    @PostMapping("/favourite/subGroup/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> addSubGroup(@RequestBody List<SubGroupDTO> subGroupId, @RequestHeader("Authorization")String token ) {
        token = token.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(username);

        groupService.addGroupToFav(user.get().getId(), null, subGroupId);
        return null;
    }

    @DeleteMapping("/favourite/{id}/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public void deleteStudent(@PathVariable UUID id) {
        userService.deleteRoleById(id, Role.ROLE_STUDENT);
    }

}
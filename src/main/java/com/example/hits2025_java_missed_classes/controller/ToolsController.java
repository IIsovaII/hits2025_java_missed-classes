package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.*;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubgroupMapper;
import com.example.hits2025_java_missed_classes.mapper.UserMapper;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.service.ToolsService;
import com.example.hits2025_java_missed_classes.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tools")
@Tag(name = "Tools")
@Validated
public class ToolsController {

    private final ToolsService toolsService;
    private final UserService userService;
    private final UserMapper userMapper;

    public ToolsController(ToolsService toolsService, UserService userService, UserMapper userMapper) {
        this.toolsService = toolsService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/student/add/group")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public ResponseEntity<?> addStudentGroup(@RequestBody @Valid StudentAddToGroupModelDto studentAddRequest) {
        toolsService.addStudentToGroup(studentAddRequest.getUserId(), studentAddRequest.getGroupName());
        return null;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public List<UserDto> getAllUsers() {
        return toolsService.getAllUsers().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/student/add/subgroup")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public ResponseEntity<?> addStudentSubgroup(@RequestBody @Valid StudentAddToSubgroupModelDto studentAddToSubgroupModelDto) {
        toolsService.addStudentToSubgroup(
                studentAddToSubgroupModelDto.getUserId(),
                studentAddToSubgroupModelDto.getSubgroupId()
        );
        return null;
    }

    @DeleteMapping("/student/delete/group")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteStudentFromGroup(@RequestBody @Valid StudentAddToGroupModelDto studentAddToGroupModelDto) {
        toolsService.deleteStudentFromGroup(studentAddToGroupModelDto.getUserId());
    }

    @DeleteMapping("/student/delete/subgroup")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteStudentFromSubgroup(@RequestBody @Valid StudentAddToSubgroupModelDto studentAddToSubgroupModelDto) {
        toolsService.deleteStudentFromSubgroup(studentAddToSubgroupModelDto.getUserId(), studentAddToSubgroupModelDto.getSubgroupId());
    }

    @PostMapping("/group/{name}/add")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void addGroup(@PathVariable String name) {
        toolsService.addGroup(name);
    }

    @PostMapping("/subgroup/{subgroupName}/add/to/group/{groupName}")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void addSubgroup(@PathVariable String groupName, @PathVariable String subgroupName) {
        toolsService.addSubgroup(subgroupName, groupName);
    }

    @DeleteMapping("/group/{groupName}/delete")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteGroup(@PathVariable String groupName) {
        toolsService.deleteGroupByName(groupName);
    }

    @DeleteMapping("/subgroup/{subgroupId}/delete")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteGroup(@PathVariable UUID subgroupId) {
        toolsService.deleteSubgroupById(subgroupId);
    }

    @PostMapping("/teacher/{id}/add")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public UUID addTeacher(@PathVariable UUID id) {
        userService.addRoleById(id, Role.ROLE_TEACHER);
        return id;
    }

    @DeleteMapping("/teacher/{id}/delete")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public UUID deleteTeacher(@PathVariable UUID id) {
        userService.deleteRoleById(id, Role.ROLE_TEACHER);
        return id;
    }
}

package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.AddSubgroupDto;
import com.example.hits2025_java_missed_classes.dto.GroupDto;
import com.example.hits2025_java_missed_classes.dto.StudentAddRequestGroupDto;
import com.example.hits2025_java_missed_classes.dto.StudentAddRequestSubgroupDto;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubgroupMapper;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.service.ToolsService;
import com.example.hits2025_java_missed_classes.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tools")
@Tag(name = "Tools")
@Validated
public class ToolsController {

    private final ToolsService toolsService;
    private final GroupMapper groupMapper;
    private final UserService userService;
    private final SubgroupMapper subgroupMapper;

    public ToolsController(ToolsService toolsService, GroupMapper groupMapper, UserService userService, SubgroupMapper subgroupMapper) {
        this.toolsService = toolsService;
        this.groupMapper = groupMapper;
        this.userService = userService;
        this.subgroupMapper = subgroupMapper;
    }

    @PostMapping("/student/add/group")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public ResponseEntity<?> addStudentGroup(@RequestBody @Valid StudentAddRequestGroupDto studentAddRequest) {
        toolsService.addStudentToGroup(studentAddRequest.getUserId(), studentAddRequest.getGroupName());
        return null;
    }

    @PostMapping("/student/add/subgroup")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public ResponseEntity<?> addStudentSubgroup(@RequestBody @Valid StudentAddRequestSubgroupDto studentAddRequestSubgroupDto) {
        toolsService.addStudentToSubgroup(studentAddRequestSubgroupDto.getUserId(), studentAddRequestSubgroupDto.getSubgroupId(), studentAddRequestSubgroupDto.getGroupName());
        return null;
    }

    @DeleteMapping("/student/delete/group")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteStudentFromGroup(@RequestBody @Valid StudentAddRequestGroupDto studentAddRequestGroupDto) {
        toolsService.deleteStudentFromGroup(studentAddRequestGroupDto.getUserId());
    }

    @DeleteMapping("/student/delete/subgroup")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteStudentFromSubgroup(@RequestBody @Valid StudentAddRequestSubgroupDto studentAddRequestSubgroupDto) {
        toolsService.deleteStudentFromSubgroup(studentAddRequestSubgroupDto.getUserId(), studentAddRequestSubgroupDto.getSubgroupId());
    }

    @PostMapping("/group/add")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void addGroup(@RequestBody @Valid GroupDto group) {
        toolsService.addGroup(groupMapper.toModel(group));
    }

    @PostMapping("/subgroup/add")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void addGroup(@RequestBody @Valid AddSubgroupDto addSubgroupDto) {
        toolsService.addSubgroup(subgroupMapper.toModel(addSubgroupDto.getSubgroup()),addSubgroupDto.getGroupName());
    }

    @DeleteMapping("/group/{groupName}/delete")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteGroup(@PathVariable String groupName) {
        toolsService.deleteGroupByName(groupName);
    }

    @DeleteMapping("/subgroup/{subgroupId}/delete")
    @PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteGroup(@PathVariable UUID subgroupId) {
        toolsService.deleteSubgroupByName(subgroupId);
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

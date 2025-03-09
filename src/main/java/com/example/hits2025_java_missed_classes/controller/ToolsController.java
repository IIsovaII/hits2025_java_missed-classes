package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.StudentAddRequest;
import com.example.hits2025_java_missed_classes.dto.SubgroupDTO;
import com.example.hits2025_java_missed_classes.dto.TeacherAddRequest;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubgroupMapper;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.Subgroup;
import com.example.hits2025_java_missed_classes.service.ToolsService;
import com.example.hits2025_java_missed_classes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tools")
@Tag(name = "Tools")
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
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public ResponseEntity<?> addStudentGroup(@RequestBody StudentAddRequest studentAddRequest) {
        toolsService.addStudentToGroup(studentAddRequest.getUserId(), studentAddRequest.getGroupName());
        return null;
    }

    @PostMapping("/student/add/subgroup")
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public ResponseEntity<?> addStudentSubgroup(@RequestBody StudentAddRequest studentAddRequest) {
        toolsService.addStudentToSubgroup(studentAddRequest.getUserId(), studentAddRequest.getSubgroupId());
        return null;
    }

    @DeleteMapping("/student/delete/subgroup")
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteStudentFromGroup(@RequestBody StudentAddRequest studentAddRequest) {
        toolsService.deleteStudentFromGroup(studentAddRequest.getUserId());
    }

    @DeleteMapping("/student/delete/group")
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteStudentFromSubgroup(@RequestBody StudentAddRequest studentAddRequest) {
        toolsService.deleteStudentFromSubgroup(studentAddRequest.getUserId(),studentAddRequest.getSubgroupId());
    }

    @PostMapping("/group/add")
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void addGroup(@RequestBody GroupDTO group) {
        toolsService.addGroup(groupMapper.toModel(group));
    }

    @PostMapping("/subgroup/add")
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void addGroup(@RequestBody SubgroupDTO subgroup) {
        toolsService.addSubgroup(subgroupMapper.toModel(subgroup));
    }

    @DeleteMapping("/group/delete")
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteGroup(@RequestBody String groupName) {
        toolsService.deleteGroupByName(groupName);
    }

    @DeleteMapping("/subgroup/{subgroupId}/delete")
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public void deleteGroup(@PathVariable UUID subgroupId) {
        toolsService.deleteSubgroupByName(subgroupId);
    }

    @PostMapping("/teacher/{id}/add")
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public UUID addTeacher(@PathVariable UUID id) {
        userService.addRoleById(id, Role.ROLE_TEACHER);
        return id;
    }

    @DeleteMapping("/teacher/{id}/delete")
    //@PreAuthorize("hasRole('ROLE_DEAN_WORKER')")
    public UUID deleteTeacher(@PathVariable UUID id) {
        userService.deleteRoleById(id, Role.ROLE_TEACHER);
        return id;
    }
}

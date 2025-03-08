package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.StudentAddRequest;
import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.dto.TeacherAddRequest;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
import com.example.hits2025_java_missed_classes.mapper.SubGroupMapper;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.SubGroup;
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

    @Autowired
    private ToolsService toolsService;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SubGroupMapper subGroupMapper;

    @PostMapping("/student/add/group")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public ResponseEntity<?> addStudentGroup(@RequestBody StudentAddRequest studentAddRequest) {
        toolsService.addStudentToGroup(studentAddRequest.getUserId(), Role.ROLE_STUDENT, studentAddRequest.getGroupName());
        return null;
    }

    @PostMapping("/student/add/subGroup")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public ResponseEntity<?> addStudentSubGroup(@RequestBody StudentAddRequest studentAddRequest) {
        toolsService.addStudentToSubGroup(studentAddRequest.getUserId(), Role.ROLE_STUDENT, studentAddRequest.getSubGroupId());
        return null;
    }

    @DeleteMapping("/student/delete/subGroup")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public void deleteStudentFromGroup(@RequestBody StudentAddRequest studentAddRequest) {
        toolsService.deleteStudentFromGroup(studentAddRequest.getUserId(),studentAddRequest.getGroupName());
    }

    @DeleteMapping("/student/delete/group")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public void deleteStudentFromSubGroup(@RequestBody StudentAddRequest studentAddRequest) {
        toolsService.deleteStudentFromSubGroup(studentAddRequest.getUserId(),studentAddRequest.getSubGroupId());
    }

    @PostMapping("/group/add")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public ResponseEntity<?> addGroup(@RequestBody GroupDTO group) {
        toolsService.addGroup(groupMapper.toModel(group));
        return null;
    }

    @PostMapping("/subGroup/add")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public ResponseEntity<?> addGroup(@RequestBody SubGroupDTO subGroup) {
        toolsService.addSubGroup(subGroupMapper.toModel(subGroup));
        return null;
    }

    @DeleteMapping("/group/delete")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public void deleteGroup(@RequestBody String groupName) {
        toolsService.deleteGroupByName(groupName);
    }

    @DeleteMapping("/subGroup/{subGroupId}/delete")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public void deleteGroup(@PathVariable UUID subGroupId) {
        toolsService.deleteSubGroupByName(subGroupId);
    }

    @Operation(summary = "add teacher worker")
    @PostMapping("/teacher/{id}/add")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public ResponseEntity<?> addTeacher(@PathVariable UUID id) {
        userService.addRoleById(id, Role.ROLE_TEACHER);
        return null;
    }

    @Operation(summary = "delete teacher worker")
    @DeleteMapping("/teacher/{id}/delete")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public ResponseEntity<?> deleteTeacher(@PathVariable UUID id) {
        userService.deleteRoleById(id, Role.ROLE_TEACHER);
        return null;
    }
}

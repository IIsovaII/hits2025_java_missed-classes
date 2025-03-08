package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.GroupDTO;
import com.example.hits2025_java_missed_classes.dto.StudentAddRequest;
import com.example.hits2025_java_missed_classes.dto.SubGroupDTO;
import com.example.hits2025_java_missed_classes.dto.TeacherAddRequest;
import com.example.hits2025_java_missed_classes.mapper.GroupMapper;
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

    @PostMapping("/student/add")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public ResponseEntity<?> addStudent(@RequestBody StudentAddRequest studentAddRequest) {
        toolsService.addStudentToGroup(studentAddRequest.getUserId(), Role.ROLE_STUDENT, studentAddRequest.getGroups(), studentAddRequest.getSubGroup());
        return null;
    }

    @DeleteMapping("/student/{id}/delete")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public void deleteStudent(@PathVariable UUID id) {
        userService.deleteRoleById(id, Role.ROLE_STUDENT);
    }

    @PostMapping("/group/add")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public ResponseEntity<?> addGroup(@RequestBody GroupDTO group) {
        toolsService.addGroup(groupMapper.toModel(group));
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
    @PostMapping("/teacher/add")
    @PreAuthorize("hasRole('ROLE_DEANWORKER')")
    public ResponseEntity<?> addTeacher(@RequestBody TeacherAddRequest request) {
        userService.addRoleById(UUID.fromString(request.getUserId()), Role.ROLE_TEACHER);
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

package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.UserAssignToRoleModelDto;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "add dean worker")
    @PostMapping("/deansWorker/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> setAsDeanWorker(@RequestBody UserAssignToRoleModelDto request) {
        return ResponseEntity.ok(
                userService.addRoleById(request.getUserId(), Role.ROLE_DEAN_WORKER)
        );
    }

    @Operation(summary = "delete dean worker")
    @DeleteMapping("/deansWorker/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> removeFromDeanWorkers(@RequestBody UserAssignToRoleModelDto request) {
        return ResponseEntity.ok(
                userService.deleteRoleById(request.getUserId(), Role.ROLE_DEAN_WORKER)
        );
    }
}

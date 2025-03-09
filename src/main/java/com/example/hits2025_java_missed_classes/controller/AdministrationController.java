package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.DeanAddRequest;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.security.JwtBlacklistService;
import com.example.hits2025_java_missed_classes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/administration")
@Tag(name = "Administration")
public class AdministrationController {
    private final JwtBlacklistService jwtBlacklistService;
    private final UserService userService;


    public AdministrationController(JwtBlacklistService jwtBlacklistService, UserService userService) {
        this.jwtBlacklistService = jwtBlacklistService;
        this.userService = userService;
    }


    @Operation(summary = "add dean worker")
    @PostMapping("/deansWorker/add")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addDeanWorker(@RequestBody DeanAddRequest request) {
        userService.addRoleById(UUID.fromString(request.getUserId()), Role.ROLE_DEAN_WORKER);
        return null;
    }

    @Operation(summary = "delete dean worker")
    @DeleteMapping("/deansWorker/delete")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteDeanWorker(@RequestBody DeanAddRequest request) {
        userService.deleteRoleById(UUID.fromString(request.getUserId()), Role.ROLE_DEAN_WORKER);
        return null;
    }

}

package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.*;
import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.UnauthorizedException;
import com.example.hits2025_java_missed_classes.mapper.UserMapper;
import com.example.hits2025_java_missed_classes.security.JwtBlacklistService;
import com.example.hits2025_java_missed_classes.security.JwtUtil;
import com.example.hits2025_java_missed_classes.service.AuthService;
import com.example.hits2025_java_missed_classes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Tag(name = "User")
@Validated
public class UserController {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(
            AuthService authService, UserService userService, UserMapper userMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Get current user")
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(userMapper.toDto(userService.getCurrentUser()));
    }

    @PostMapping("/register")
    public TokenResponseDto registerUser(@Valid @RequestBody RegisterRequestDto request, HttpServletRequest servletRequest) {
        return authService.registerUser(request, servletRequest);
    }

    @PostMapping("/login")
    public TokenResponseDto loginUser(@Valid @RequestBody LoginRequestDto request, HttpServletRequest servletRequest) {
        return authService.authenticate(request, servletRequest);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        return authService.logout(request);
    }
}
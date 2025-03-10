package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.*;
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
    private final JwtBlacklistService jwtBlacklistService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(
            AuthService authService, JwtBlacklistService jwtBlacklistService, JwtUtil jwtUtil, UserService userService, UserMapper userMapper) {
        this.authService = authService;
        this.jwtBlacklistService = jwtBlacklistService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Get current user")
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(userMapper.toDto(userService.getCurrentUser()));
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> registerUser(@RequestBody @Valid RegisterRequestDto request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid LoginRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // TODO: тут проверить что токен вообще валидный, чтобы не было 500 ошибки
            if (!jwtBlacklistService.isBlacklisted(token)) {
                long expirationTime = jwtUtil.getExpirationTimeFromToken(token);
                jwtBlacklistService.addToBlacklist(token, expirationTime);
                return ResponseEntity.ok("Logged out successfully");
            } else {
                return ResponseEntity.badRequest().body("Token is already blacklisted");
            }
        }

        return ResponseEntity.badRequest().body("Invalid token");
    }
}
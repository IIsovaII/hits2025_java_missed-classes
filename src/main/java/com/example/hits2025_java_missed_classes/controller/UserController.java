package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.LoginRequest;
import com.example.hits2025_java_missed_classes.dto.LoginResponse;
import com.example.hits2025_java_missed_classes.dto.RegisterRequest;
import com.example.hits2025_java_missed_classes.dto.RegisterResponse;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.security.JwtBlacklistService;
import com.example.hits2025_java_missed_classes.security.JwtUtil;
import com.example.hits2025_java_missed_classes.service.AuthService;
import com.example.hits2025_java_missed_classes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@Tag(name = "User")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtBlacklistService jwtBlacklistService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserController(
            UserService userService, AuthService authService, JwtBlacklistService jwtBlacklistService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.authService = authService;
        this.jwtBlacklistService = jwtBlacklistService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    // TODO: на данный момент если в хедере на месте токена записана какая-то фигня типв "123" вылетит ошибка
    @Operation(summary = "Get current user")
    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // TODO: тут проверить что токен вообще валидный, чтобы не было 500 ошибки
            if (!jwtBlacklistService.isBlacklisted(token)) {
                String username = jwtUtil.extractUsername(token); // тут username тоже email
                Optional<User> user = userRepository.findByEmail(username);
                return ResponseEntity.ok(user);
            } else {
                // TODO: переписать ответ и выкинуть 401
                return ResponseEntity.badRequest().body("Token is already blacklisted");
            }
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest request) {
        return authService.authenticate(request);
    }

    // TODO: на данный момент если в хедере на месте токена записана какая-то фигня типв "123" вылетит ошибка
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
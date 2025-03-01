package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.AuthRequest;
import com.example.hits2025_java_missed_classes.dto.AuthResponse;
import com.example.hits2025_java_missed_classes.dto.RegisterRequest;
import com.example.hits2025_java_missed_classes.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

}
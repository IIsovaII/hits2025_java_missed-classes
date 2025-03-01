package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.AuthRequest;
import com.example.hits2025_java_missed_classes.dto.AuthResponse;
import com.example.hits2025_java_missed_classes.dto.RegisterRequest;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Хешируем пароль
        userRepository.save(user);
        return "User registered successfully!";
    }

    //    @PostMapping("/register")
    //    public String register(@RequestBody RegisterRequest registerRequest) {
    //        return authService.register(registerRequest);
    ////        return "User registered successfully!";
    //    }

}
package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.AuthRequest;
import com.example.hits2025_java_missed_classes.dto.AuthResponse;
import com.example.hits2025_java_missed_classes.dto.RegisterRequest;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String register(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setPatronymic(registerRequest.getPatronymic());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Хешируем пароль

        userRepository.save(user);

        // Аутентификация пользователя
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(registerRequest.getEmail());

        return jwtUtil.generateToken(String.valueOf(userDetails));
    }


    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

            final String jwt = jwtUtil.generateToken(String.valueOf(userDetails));

            return new AuthResponse(jwt);
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            throw e;
        }
    }
}


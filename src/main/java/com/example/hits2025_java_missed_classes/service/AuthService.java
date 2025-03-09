package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.LoginRequest;
import com.example.hits2025_java_missed_classes.dto.LoginResponse;
import com.example.hits2025_java_missed_classes.dto.RegisterRequest;
import com.example.hits2025_java_missed_classes.dto.RegisterResponse;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.security.CustomUserDetails;
import com.example.hits2025_java_missed_classes.security.CustomUserDetailsService;
import com.example.hits2025_java_missed_classes.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse authenticate(LoginRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        return new LoginResponse(jwt);
    }

    @Transactional
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User with this username already exists.");
        }

        User user = new User();
        user.setName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setSurname(registerRequest.getSurname());
        user.setPatronymic(registerRequest.getPatronymic());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Set.of(Role.ROLE_USER));

        //TODO УБРАТЬ
        user.setRoles(Set.of(Role.ROLE_USER, Role.ROLE_ADMIN, Role.ROLE_DEAN_WORKER, Role.ROLE_STUDENT, Role.ROLE_TEACHER));

        userRepository.save(user);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        return new RegisterResponse(jwt);
    }
}

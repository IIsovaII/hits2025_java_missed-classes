package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.dto.LoginRequest;
import com.example.hits2025_java_missed_classes.dto.RegisterRequest;
import com.example.hits2025_java_missed_classes.dto.TokenResponse;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider tokenProvider,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


        public TokenResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = tokenProvider.generateToken(authentication);

        return new TokenResponse(jwt);
    }

    @Transactional
    public TokenResponse registerUser(RegisterRequest registerRequest) {
        // Прверка на отсутствие в БД пользователя с такой почтой
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists.");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setPatronymic(registerRequest.getPatronymic());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(registerRequest.getRoles());

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail(),
                        registerRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = tokenProvider.generateToken(authentication);
        return new TokenResponse(jwt);
    }
}

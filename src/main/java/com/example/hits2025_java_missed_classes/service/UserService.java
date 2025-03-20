package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.exception.base_status_code_exceptions.UnauthorizedException;
import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addRoleById(UUID id, Role role) {
        User user = userRepository.getReferenceById(id);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void deleteRoleById(UUID id, Role role) {
        User user = userRepository.getReferenceById(id);
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Unauthorized"));
    }
}
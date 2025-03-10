package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public boolean addRoleById(UUID id, Role role) {
        User user = userRepository.getReferenceById(id);
        user.getRoles().add(role);
        userRepository.save(user);
        return true;
    }

    public boolean deleteRoleById(UUID id, Role role) {
        User user = userRepository.getReferenceById(id);
        user.getRoles().remove(role);
        userRepository.save(user);
        return true;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find user with email: " + email));
    }
}
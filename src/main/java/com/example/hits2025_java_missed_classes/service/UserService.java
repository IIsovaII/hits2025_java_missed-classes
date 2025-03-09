package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.model.Role;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.repository.UserRepository;
import com.example.hits2025_java_missed_classes.security.CustomUserDetailsService;
import com.example.hits2025_java_missed_classes.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public boolean addRoleById(UUID id, Role role) {
        User user = userRepository.getReferenceById(id);
        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    public boolean deleteRoleById(UUID id, Role role) {
        User user = userRepository.getReferenceById(id);
        List<Role> roles = user.getRoles();
        roles.remove(role);
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

//    public UUID currentUserId(){
//        return userId;
//    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository
                .findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Unable to find user with email: " + username));
    }
}

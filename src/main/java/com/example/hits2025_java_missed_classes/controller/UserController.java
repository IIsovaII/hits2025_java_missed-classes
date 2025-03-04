package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.LoginRequest;
import com.example.hits2025_java_missed_classes.dto.RegisterRequest;
import com.example.hits2025_java_missed_classes.dto.TokenResponse;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.service.AuthService;
import com.example.hits2025_java_missed_classes.service.BlacklistService;
import com.example.hits2025_java_missed_classes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO: изменить url запроса
@RestController
@RequestMapping("/account")
public class UserController {
    private final AuthService authService;
    private final BlacklistService blacklistService;
    private final UserService userService;

    public UserController(AuthService authService,
                          BlacklistService blacklistService,
                          UserService userService) {
        this.authService = authService;
        this.blacklistService = blacklistService;
        this.userService = userService;
    }


    // TODO: заблокировать для неавторизированных и тех что в блеклисте, исправить формат вывода
    // Пока может вылетать ошибка когда вы с некорректным токеном в хедере попытаетесь выполнить этот запрос
    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        User user = userService.getUserByToken(token);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        TokenResponse userId = authService.registerUser(registerRequest);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        TokenResponse token = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token) {
        blacklistService.logoutUser(token);
        return ResponseEntity.ok().build();
    }
}

package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.dto.DeanAddRequest;
import com.example.hits2025_java_missed_classes.model.User;
import com.example.hits2025_java_missed_classes.security.JwtBlacklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/administration")
@Tag(name = "Administration")
public class AdministrationController {
    private final JwtBlacklistService jwtBlacklistService;


    public AdministrationController(JwtBlacklistService jwtBlacklistService) {
        this.jwtBlacklistService = jwtBlacklistService;
    }


    @Operation(summary = "add dean worker")
    @PostMapping("/deansWorker/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addDeanWorker(HttpServletRequest headers, @RequestBody DeanAddRequest request) {
        String authorizationHeader = headers.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // TODO: тут проверить что токен вообще валидный, чтобы не было 500 ошибки
            if (!jwtBlacklistService.isBlacklisted(token)) {

                return ResponseEntity.ok().body("You are admin");
            } else {
                // TODO: переписать ответ и выкинуть 401
                return ResponseEntity.badRequest().body("");
            }
        }
        return null;
    }
}

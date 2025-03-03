package com.example.hits2025_java_missed_classes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO: просто шаблоны прописаны, нужно сделать весь функционал
@RestController
@RequestMapping("/requests")
public class RequestsController {
    @GetMapping
    public ResponseEntity<?> getRequests() {
        return ResponseEntity.ok("get requests");
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyRequests() {
        return ResponseEntity.ok("my requests");
    }

    @PostMapping
    public ResponseEntity<?> createRequest() {
        return ResponseEntity.ok("create request");
    }

    @PostMapping("/{id}/confirmation")
    public ResponseEntity<?> attachDocToRequest(@PathVariable int id) {
        return ResponseEntity.ok("attach doc");
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editRequest(@PathVariable int id) {
        return ResponseEntity.ok("edit request");
    }

    @PutMapping("/{id}/prolong")
    public ResponseEntity<?> prolongRequest(@PathVariable int id) {
        return ResponseEntity.ok("prolong");
    }
}

package com.example.hits2025_java_missed_classes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: просто шаблоны прописаны, нужно сделать весь функционал
@RestController
@RequestMapping("/students")
public class StudentsController {
    // TODO: что обозначает название?
    @GetMapping("/gant")
    public ResponseEntity<?> getStudentsByFilters() {
        return ResponseEntity.ok("gant");
    }

    @GetMapping
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok("students");
    }
}

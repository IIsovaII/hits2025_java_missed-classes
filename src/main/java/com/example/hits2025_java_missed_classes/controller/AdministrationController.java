package com.example.hits2025_java_missed_classes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO: просто шаблоны прописаны, нужно сделать весь функционал
@RestController
@RequestMapping("/administration")
public class AdministrationController {
    @GetMapping("/faculty")
    public ResponseEntity<?> getFacultiesList() {
        return ResponseEntity.ok("Faculty List");
    }

    @PostMapping("/faculty")
    public ResponseEntity<?> registerFaculty(String facultyName) {
        return ResponseEntity.ok("Faculty Registered");
    }

    // TODO: нафига тут /add ?
    @PostMapping("/deansWorker/add")
    public ResponseEntity<?> addDeansWorker(String userId, String facultyName) {
        return ResponseEntity.ok("Deans Worker");
    }

    // TODO: Удаление в целом или только из одного факультета? и не стоит ли убрать delete из конца запроса?
    @DeleteMapping("/deansWorker/delete")
    public ResponseEntity<?> deleteDeansWorker(String userId, String facultyName) {
        return ResponseEntity.ok("Deans Worker");
    }

    // TODO: ну вообще надо добавить такой функционал, но в сваггере мы такое не прописывали
    @DeleteMapping("/faculty")
    public ResponseEntity<?> deleteFaculty(String facultyName) {
        return ResponseEntity.ok("Faculty Delete");
    }
}

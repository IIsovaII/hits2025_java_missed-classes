package com.example.hits2025_java_missed_classes.controller;

import com.example.hits2025_java_missed_classes.model.FacultyEntity;
import com.example.hits2025_java_missed_classes.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: просто шаблоны прописаны, нужно сделать весь функционал
// TODO: проверка на роль пользователя перед доступом к функциям
@RestController
@RequestMapping("/administration")
public class AdministrationController {
    private final FacultyService facultyService;

    public AdministrationController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/faculty")
    public ResponseEntity<?> getFacultiesList() {
        List<FacultyEntity> faculties = facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    @PostMapping("/faculty")
    public ResponseEntity<?> addFaculty(String facultyName) {
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

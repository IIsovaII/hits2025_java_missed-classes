package com.example.hits2025_java_missed_classes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: просто шаблоны прописаны, нужно сделать весь функционал
@RestController
@RequestMapping("/tools")
public class ToolsController {
    @PostMapping("/student/add")
    public ResponseEntity<?> setStudentGroup(){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("student/delete")
    public ResponseEntity<?> deleteStudentGroup(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/teacher/add")
    public ResponseEntity<?> addTeacher(){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/teacher/delete")
    public ResponseEntity<?> deleteTeacher(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/group/add")
    public ResponseEntity<?> createGroup(){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/group/delete")
    public ResponseEntity<?> deleteGroup(){
        return ResponseEntity.ok().build();
    }
}

package com.example.hits2025_java_missed_classes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Это все функционал учителя
// TODO: просто шаблоны прописаны, нужно сделать весь функционал
@RestController
@RequestMapping("/group")
public class GroupController {
    @GetMapping("/favourite")
    public ResponseEntity<?> favouriteGroup() {
        return ResponseEntity.ok("favourite groups list");
    }

    // TODO: может просто search?
    @GetMapping("/searchGroup")
    public ResponseEntity<?> searchGroup() {
        return ResponseEntity.ok("search group");
    }

    // TODO: зачем add в конце?
    @PostMapping("/favourite/add")
    public ResponseEntity<?> addFavouriteGroup() {
        return ResponseEntity.ok("add favourite group");
    }

    // TODO: ну тоже может без delete?
    @DeleteMapping("/favourite/delete")
    public ResponseEntity<?> deleteFavouriteGroup() {
        return ResponseEntity.ok("delete favourite group");
    }
}

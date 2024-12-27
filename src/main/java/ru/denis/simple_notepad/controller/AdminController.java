package ru.denis.simple_notepad.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/home")
    private ResponseEntity<String> home() {
        return ResponseEntity.ok("Admin home");
    }
}

package ru.denis.simple_notepad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
}

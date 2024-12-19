package ru.denis.simple_notepad.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class MainPageController {
    @GetMapping()
    @PreAuthorize("hasAuthority('people:read')")
    public String mainPage() {
        return "index";
    }

}

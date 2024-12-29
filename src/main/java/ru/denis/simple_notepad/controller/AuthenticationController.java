package ru.denis.simple_notepad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.denis.simple_notepad.model.Person;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
//
//    @GetMapping("/success")
//    public String successPage() {
//        return "success";
//    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        Person person = new Person();

        model.addAttribute("person", person);

        return "register";
    }
}

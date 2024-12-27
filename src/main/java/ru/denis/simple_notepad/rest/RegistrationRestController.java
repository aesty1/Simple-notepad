package ru.denis.simple_notepad.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.denis.simple_notepad.model.Person;
import ru.denis.simple_notepad.model.Status;
import ru.denis.simple_notepad.repository.PeopleRepository;

import java.util.Date;

@RestController
@RequestMapping("/register")
public class RegistrationRestController {
    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public Person createPerson(@ModelAttribute Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setCreated_at(new Date());
        person.setRole("USER");
        person.setStatus("ACTIVE");
        return peopleRepository.save(person);
    }
}

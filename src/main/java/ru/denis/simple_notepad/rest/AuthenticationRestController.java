package ru.denis.simple_notepad.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.denis.simple_notepad.jwt.JwtProvider;
import ru.denis.simple_notepad.model.LoginForm;
import ru.denis.simple_notepad.model.Person;
import ru.denis.simple_notepad.repository.PeopleRepository;
import ru.denis.simple_notepad.service.PeopleService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationRestController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtProvider provider;

    @Autowired
    private PeopleService peopleService;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginForm loginForm) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));

        if(authentication.isAuthenticated()) {
            return provider.createToken(peopleService.loadUserByUsername(loginForm.username()));
        } else {
            throw new UsernameNotFoundException("Bad credentials");
        }
    }
}

package ru.denis.simple_notepad.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.denis.simple_notepad.jwt.JwtProvider;
import ru.denis.simple_notepad.model.Person;
import ru.denis.simple_notepad.repository.PeopleRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {
    private final AuthenticationManager manager;
    private PeopleRepository repository;
    private JwtProvider provider;

    @Autowired
    public AuthenticationRestController(AuthenticationManager manager, PeopleRepository repository, JwtProvider provider) {
        this.manager = manager;
        this.repository = repository;
        this.provider = provider;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO request) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            Person person = repository.findByUsername(request.getUsername());
            String token = provider.createToken(request.getUsername(), person.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", request.getUsername());
            response.put("token", token);

            return ResponseEntity.ok(response);
        }
        catch(AuthenticationException e) {
            return new ResponseEntity<>("Invalid username/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();

        handler.logout(request, response, null);
    }
}

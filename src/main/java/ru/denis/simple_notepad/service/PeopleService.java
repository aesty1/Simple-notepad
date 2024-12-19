package ru.denis.simple_notepad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.denis.simple_notepad.model.Person;
import ru.denis.simple_notepad.repository.PeopleRepository;
import ru.denis.simple_notepad.security.PersonSecurity;

@Service
public class PeopleService implements UserDetailsService {
    private final PeopleRepository repository;

    @Autowired
    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = repository.findByUsername(username);
        System.out.println("its work");
        return PersonSecurity.fromPerson(person);
    }

    public UserDetailsService userDetailsService() {
        return this::loadUserByUsername;
    }
}

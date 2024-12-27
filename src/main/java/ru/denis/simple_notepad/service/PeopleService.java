package ru.denis.simple_notepad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.denis.simple_notepad.model.Person;
import ru.denis.simple_notepad.repository.PeopleRepository;

import java.util.Optional;

@Service
public class PeopleService implements UserDetailsService {
    private final PeopleRepository repository;

    @Autowired
    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = repository.findByUsername(username);

        if(user.isPresent()) {
            var userObj = user.get();

            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(Person person) {
        if(person.getRole() == null) {
            return new String[]{"USER"};
        }

        return person.getRole().split(",");
    }

}

package ru.denis.simple_notepad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denis.simple_notepad.model.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);
}

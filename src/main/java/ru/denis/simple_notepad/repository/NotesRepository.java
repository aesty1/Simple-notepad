package ru.denis.simple_notepad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denis.simple_notepad.model.Note;
import ru.denis.simple_notepad.model.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotesRepository extends JpaRepository<Note, Integer> {
    List<Note> findNotesByPerson(Person person);

    Optional<Note> findByIdAndPerson(int id, Person person);
}

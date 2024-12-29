package ru.denis.simple_notepad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.denis.simple_notepad.model.Person;

import java.util.Date;

@Getter
@Setter
public class NoteDTO {
    private int id;
    private String title;
    private String content;
    private Date created_at;
    private Person person;

    public NoteDTO() {
    }
}

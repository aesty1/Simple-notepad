package ru.denis.simple_notepad.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "note")
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Date created_at;
}

package ru.denis.simple_notepad.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import java.util.Date;

@Entity
@Table(name = "person")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int person_id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "role")
    private String role;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

}

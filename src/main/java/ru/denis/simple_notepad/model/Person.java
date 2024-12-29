package ru.denis.simple_notepad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 5, max = 64, message = "Username must be between 5 and 64 characters")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
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

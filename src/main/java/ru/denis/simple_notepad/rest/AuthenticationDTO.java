package ru.denis.simple_notepad.rest;

import lombok.Data;

@Data
public class AuthenticationDTO {
    private String username;
    private String password;
}

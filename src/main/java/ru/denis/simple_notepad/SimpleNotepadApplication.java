package ru.denis.simple_notepad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories(basePackages = "ru.denis.simple_notepad.repository")
public class SimpleNotepadApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleNotepadApplication.class, args);
    }

}

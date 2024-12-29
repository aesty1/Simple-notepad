package ru.denis.simple_notepad.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.denis.simple_notepad.model.Note;
import ru.denis.simple_notepad.model.Person;
import ru.denis.simple_notepad.model.dto.NoteDTO;
import ru.denis.simple_notepad.repository.NotesRepository;
import ru.denis.simple_notepad.repository.PeopleRepository;
import ru.denis.simple_notepad.service.NotesService;
import ru.denis.simple_notepad.service.PeopleService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/notes")
public class NotesRestController {
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private NotesService notesService;
    @Autowired
    private PeopleRepository peopleRepository;


    @GetMapping()
    public String getAll(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("notes", notesService.findNotesByPerson(username).stream().map(note -> {
            NoteDTO dto = new NoteDTO();

            dto.setId(note.getId());
            dto.setTitle(note.getTitle());
            dto.setContent(note.getContent());
            dto.setCreated_at(note.getCreated_at());
            dto.setPerson(note.getPerson());

            return dto;
        }).toList());

        return "getAllNotes";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable int id, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Note note = notesService.findByIdAndPerson(id, username);

        model.addAttribute("note", note);

        return "getOneNote";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable int id, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Note note = notesService.findByIdAndPerson(id, username);

        model.addAttribute("note", note);

        return "editNote";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @ModelAttribute Note note) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        note.setPerson(peopleRepository.findByUsername(username).orElse(null));
        note.setId(id);

        notesRepository.save(note);

        return "redirect:/notes/" + id;
    }

    @PostMapping("/{id}/auto-save")
    @ResponseBody
    public String autoSave(@PathVariable int id, @RequestParam String title, @RequestParam String content) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Note note = notesRepository.findById(id).orElse(null);
        if (note != null) {
            note.setTitle(title);
            note.setContent(content);
            note.setPerson(peopleRepository.findByUsername(username).orElse(null));

            notesRepository.save(note);
            return "Сохранено";
        } else {
            return "Ошибка сохранения";
        }
    }

    @PostMapping()
    public String create(@ModelAttribute Note note) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        note.setTitle("New note");
        note.setContent(" ");
        note.setPerson(peopleRepository.findByUsername(username).orElse(null));
        note.setCreated_at(new Date());

        notesRepository.save(note);

        return "redirect:/notes/" + note.getId();
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable int id) {
        notesRepository.deleteById(id);

        return "redirect:/notes";
    }
}

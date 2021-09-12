package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.UserNote;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/notes")
public class NoteController {
    private Logger logger = LoggerFactory.getLogger(NoteController.class);
    private NoteService noteService;
    public NoteController(NoteService noteService) {this.noteService = noteService;}

    @PostMapping("/note")
    public String noteSubmit(
            @ModelAttribute("userNote")UserNote userNote,
            @RequestParam(required = false, name="noteTitle") String noteTitle,
            @RequestParam(required = false, name="noteDescription") String noteDescription,
            Authentication authentication,
            Model model) {
        String username = authentication.getPrincipal().toString();

            if(noteTitle.isEmpty()) {
                return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
            } else if(noteDescription.isEmpty()) {
                return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
            }else {
                this.logger.error("Submitted Note: " + userNote.toString());
                Boolean isSuccess = this.noteService.insertOrUpdateNoteByUser(username, userNote);
                return "redirect:/result?isSuccess=" + isSuccess;
            }
    }

    @GetMapping("/note")
    public String noteDeletion(
            @ModelAttribute("userNote") UserNote userNote,
            @RequestParam(required = false, name = "noteId") Integer noteId,
            Authentication authentication,
            Model model) {
        String username = authentication.getPrincipal().toString();
        this.logger.error(noteId.toString());
        Boolean isSuccess = this.noteService.deleteNote(noteId, username);
        return "redirect:/result?isSuccess=" + isSuccess;
    }
}

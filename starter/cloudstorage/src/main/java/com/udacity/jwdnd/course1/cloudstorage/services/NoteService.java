package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserNoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.UserNote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteService {

    private Logger logger = LoggerFactory.getLogger(NoteService.class);
    private NoteMapper noteMapper;
    private UserNoteMapper userNoteMapper;

    public NoteService(NoteMapper noteMapper, UserNoteMapper userNoteMapper) {
        this.noteMapper = noteMapper;
        this.userNoteMapper = userNoteMapper;
    }

    public List<UserNote> getNotesByUsername(String username) {
        return this.userNoteMapper.getNotesByUsername(username);
    }

    public Boolean insertOrUpdateNoteByUser(String username, UserNote userNote) {
        String noteTitle = userNote.getNoteTitle();
        String noteDescription = userNote.getNoteDescription();
        Integer noteId = userNote.getNoteId();

        if (noteId == null || noteId.toString() == "") {
            this.userNoteMapper.insertNoteByUsername(username, noteTitle, noteDescription);
        } else {
            this.noteMapper.update(noteTitle, noteDescription, noteId);
        }
        return true;
    }

    public boolean deleteNote(Integer noteId, String username) {
        this.noteMapper.delete(noteId);
        return true;
    }
}

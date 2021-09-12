package com.udacity.jwdnd.course1.cloudstorage.MapperTests;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
public class NoteTest {

    private Logger logger = LoggerFactory.getLogger(NoteTest.class);

    @Autowired
    private NoteMapper noteMapper;

    @Test
    public void insertNote() {
        Note newNote = new Note(null, "Hello CA", "CA SOFTWARE TESTING");
        this.noteMapper.insert(newNote);

        Note savedNote = this.noteMapper.getNoteById(newNote.getNoteid());

        Assertions.assertNotNull(savedNote);
        Assertions.assertEquals(newNote.getNotetitle(), savedNote.getNotetitle());
        Assertions.assertEquals(newNote.getNotedescription(), savedNote.getNotedescription());
    }

    @Test
    public void updateNote() {
        Note newNote = new Note(null, "Hello CA", "CA SOFTWARE TESTING");
        this.noteMapper.insert(newNote);
        Integer noteId = newNote.getNoteid();
        Assertions.assertNotNull(noteId);

        this.noteMapper.update("Hello CANSU", "This is a test note.", noteId);

        Note note = this.noteMapper.getNoteById(noteId);

        Assertions.assertNotNull(note);
        Assertions.assertEquals("Hello CANSU", note.getNotetitle());
        Assertions.assertEquals("This is a test note.", note.getNotedescription());
    }

    @Test
    public void deleteNote() {
        Note newNote = new Note(null, "Hello CA", "CA SOFTWARE TESTING");
        this.noteMapper.insert(newNote);

        Integer noteId = newNote.getNoteid();

        Assertions.assertNotNull(noteId);
        this.noteMapper.delete(noteId);

        Note note = this.noteMapper.getNoteById(noteId);
        Assertions.assertNull(note);

    }

    @Test
    public void deleteAllNotes() {
        Note newNote = new Note(null, "Hello CA", "CA SOFTWARE TESTING");
        this.noteMapper.insert(newNote);

        List<Note> notes = this.noteMapper.getAllNotes();
        Assertions.assertFalse(notes.isEmpty());
        this.noteMapper.deleteAll();
        notes = this.noteMapper.getAllNotes();
        Assertions.assertTrue(notes.isEmpty());
    }
}

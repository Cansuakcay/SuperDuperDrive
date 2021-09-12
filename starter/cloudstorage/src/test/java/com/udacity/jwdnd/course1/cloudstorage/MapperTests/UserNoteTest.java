package com.udacity.jwdnd.course1.cloudstorage.MapperTests;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserNoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.UserNote;
import org.junit.Before;
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
public class UserNoteTest {
    public static final String USERNAME = "byu00";
    public static final String NOTE_TITLE = "hello-world";
    public static final String NOTE_TITLE_I = "hello world";
    public static final String NOTE_DESCRIPTION = "world";
    public static final String NOTE_DESCRIPTION_I = "world-3";

    private Logger logger = LoggerFactory.getLogger(UserNoteTest.class);

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserNoteMapper userNoteMapper;

    @Before
    public void beforeAll() {

        User user = new User(
                USERNAME,
                "12345",
                "1988",
                "Hello",
                "Cansu");

        this.userMapper.insert(user);

        this.userNoteMapper.insertNoteByUsername(USERNAME, NOTE_TITLE, NOTE_DESCRIPTION);
    }

    @Test
    public void insertNoteByUsername() {

        User user = this.userMapper.getUserByUsername(USERNAME);

        List<UserNote> userNoteList = this.userNoteMapper.getNotesByUsername(USERNAME);

        Assertions.assertFalse(userNoteList.isEmpty());

        UserNote userNote = userNoteList.get(0);

        Assertions.assertEquals(NOTE_TITLE, userNote.getNoteTitle());
        Assertions.assertEquals(NOTE_DESCRIPTION, userNote.getNoteDescription());
        Assertions.assertEquals(user.getUserid(), userNote.getUserId());
    }

}

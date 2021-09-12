package com.udacity.jwdnd.course1.cloudstorage.MapperTests;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.UserFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
public class UserFileTest {
    public final static String USERNAME = "cansu";
    public final static String FILE_NAME = "hello-cansu";

    private Integer userId;
    private Integer fileId;

    private Logger logger = LoggerFactory.getLogger(FileTest.class);

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserFileMapper userFileMapper;

    @Before
    public void before() {

        User user = new User(
                USERNAME,
                "1242",
                "1988",
                "Hello",
                "Cansu");

        this.userMapper.insert(user);

        this.userId = user.getUserid();

        File file = new File(
                null,
                FILE_NAME,
                "txt",
                "2MB",
                this.userId,
                null);

        this.fileMapper.insert(file);

        this.fileId = file.getFileid();
    }

    @Test
    public void getFileByUsername() {

        Assertions.assertNotNull(this.userId);

        Assertions.assertNotNull(this.fileId);

        List<UserFile> userFileList = this.userFileMapper.getFileByUsername(USERNAME);

        Assertions.assertFalse(userFileList.isEmpty());

        UserFile userFile = userFileList.get(0);

        Assertions.assertNotNull(userFile);

        Assertions.assertEquals(userId, userFile.getUserId());
        Assertions.assertEquals(fileId, userFile.getFileId());
        Assertions.assertEquals(FILE_NAME, userFile.getFileName());
    }

    @Test
    public void getFileByUsernameAndFileName() {

        Map<String, Object> paraMap = new HashMap<>();

        paraMap.put("username", USERNAME);
        paraMap.put("filename", FILE_NAME);

        List<UserFile> userFileList =
                this.userFileMapper.getFileByUsernameAndFileName(paraMap);

        Assertions.assertFalse(userFileList.isEmpty());

        UserFile userFile = userFileList.get(0);

        Assertions.assertNotNull(userFile);

        Assertions.assertEquals(userId, userFile.getUserId());
        Assertions.assertEquals(fileId, userFile.getFileId());
        Assertions.assertEquals(FILE_NAME, userFile.getFileName());
    }
}

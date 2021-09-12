package com.udacity.jwdnd.course1.cloudstorage.MapperTests;


import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserCredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.UserCredential;
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
public class UserCredentialTest {

    public static final String USERNAME_U = "CA";
    public static final String USERNAME_C = "Cansu";
    public static final String PASSWORD = "hello-cansu";
    public static final String FIRSTNAME = "cansu";
    public static final String LASTNAME = "a";
    public static final String SALT = "dsffdgdfgfdas";
    public static final String URL = "http://www.casoftwaretesting.co.uk";
    public static final String URL_I = "https://github.com/cansuakcay";

    private Logger logger = LoggerFactory.getLogger(UserCredentialTest.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private UserCredentialMapper userCredentialMapper;

    @Before
    public void before() {

        this.insertUser();
    }

    private void insertUser() {

        User user = new User(USERNAME_U, SALT, PASSWORD, FIRSTNAME, LASTNAME);

        this.userMapper.insert(user);
    }

    @Test
    public void getCredentialsByUsername() {

        User user = this.userMapper.getUserByUsername(USERNAME_U);

        Assertions.assertNotNull(user);

        Integer userId = user.getUserid();

        Credential newCredential = new Credential(
                null, URL, USERNAME_C, SALT, PASSWORD, userId);

        this.credentialMapper.insert(newCredential);

        Integer credentialId = newCredential.getCredentialid();

        Assertions.assertNotNull(credentialId);

        List<UserCredential> userCredentialList =
                this.userCredentialMapper.getCredentialsByUsername(USERNAME_U);

        Assertions.assertFalse(userCredentialList.isEmpty());

        UserCredential userCredential = userCredentialList.get(0);

        Assertions.assertEquals(USERNAME_C, userCredential.getUsername());
        Assertions.assertEquals(SALT, userCredential.getKey());
        Assertions.assertEquals(PASSWORD, userCredential.getPassword());
    }

    @Test
    public void insertCredentialByUsername() {

        User user = this.userMapper.getUserByUsername(USERNAME_U);

        Assertions.assertNotNull(user);

        this.userCredentialMapper.insertCredentialByUsername(URL_I, USERNAME_C, SALT, PASSWORD, USERNAME_U);

        List<UserCredential> userCredentialList =
                this.userCredentialMapper.getCredentialsByUsername(USERNAME_U);

        Assertions.assertFalse(userCredentialList.isEmpty());

        UserCredential userCredential = userCredentialList.get(0);

        Assertions.assertEquals(USERNAME_C, userCredential.getUsername());
        Assertions.assertEquals(SALT, userCredential.getKey());
        Assertions.assertEquals(PASSWORD, userCredential.getPassword());
        Assertions.assertEquals(URL_I, userCredential.getUrl());
    }


}

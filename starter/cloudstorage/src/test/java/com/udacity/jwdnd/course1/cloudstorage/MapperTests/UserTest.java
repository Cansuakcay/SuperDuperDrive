package com.udacity.jwdnd.course1.cloudstorage.MapperTests;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
public class UserTest {
    private Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertUser() {

        User newUser = new User(
                "cansu",
                "sadasdas",
                "1988",
                "hello",
                "cansu");

        this.userMapper.insert(newUser);

        Integer userId = newUser.getUserid();

        Assertions.assertNotNull(userId);

        User user = this.userMapper.getUserById(userId);

        Assertions.assertNotNull(user);

        Assertions.assertEquals(newUser.getFirstname(), user.getFirstname());
        Assertions.assertEquals(newUser.getLastname(), user.getLastname());
    }

    @Test
    public void getAllUsers() {

        User newUser = new User(
                "cansu",
                "sadasdas",
                "1988",
                "hello",
                "cansu");

        this.userMapper.insert(newUser);

        List<User> userList = this.userMapper.getAllUsers();

        Assertions.assertFalse(userList.isEmpty());
        Assertions.assertTrue(userList.size() == 1);

        User user = userList.get(0);

        Assertions.assertEquals(newUser.getUsername(), user.getUsername());
    }

    @Test
    public void deleteUser() {

        User newUser = new User(
                "cansu",
                "sadasdas",
                "1988",
                "hello",
                "cansu");

        this.userMapper.insert(newUser);

        Integer userId = newUser.getUserid();

        this.userMapper.delete(userId);

        User user = this.userMapper.getUserById(userId);

        Assertions.assertNull(user);
    }
}

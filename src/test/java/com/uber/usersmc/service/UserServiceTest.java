package com.uber.usersmc.service;

import com.uber.usersmc.entity.User;
import com.uber.usersmc.repository.UserDao;
import org.hibernate.PropertyNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;
    private User user;


    @Before
    public void setUp() {
        user = User.builder()
                .firstName("Cristian")
                .lastName("Sanchez")
                .phoneNumber(789456123)
                .build();

        userDao.save(user);
    }

    @Test
    public void testGettingUser() {
        User userResponse = userService.getUserById(user.getId());
        assertNotNull(user);
        assertEquals(user, userResponse);
    }

    @Test(expected = PropertyNotFoundException.class)
    public void testGettingUserNotFound() {
        userService.getUserById(2L);
    }

    @Test
    public void testGettingAllUsers() {
        List<User> userList = userDao.findAll();
        assertEquals(userList.size(), 1);
        assertTrue(userList.contains(user));
    }

    @Test
    public void testSavingUser() {
        User user = User.builder()
                .firstName("Cristian")
                .lastName("Sanchez")
                .phoneNumber(789456123)
                .build();
        User userSave = userService.createUser(user);
        assertNotNull(userSave);
        assertNotNull(userSave.getId());
    }

}

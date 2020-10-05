package com.uber.usersmc.service;

import com.uber.usersmc.repository.UserDao;
import com.uber.usersmc.entity.User;
import lombok.AllArgsConstructor;
import org.hibernate.MappingException;
import org.hibernate.PropertyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements Accounts {

    private final UserDao userDao;

    @Override
    public User createUser(User user) {
        return userDao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Long id) throws MappingException {
        return userDao.findById(id).orElseThrow(() -> new PropertyNotFoundException("User not found"));
    }

}

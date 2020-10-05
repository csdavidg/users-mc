package com.uber.usersmc.service;

import com.uber.usersmc.entity.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface Accounts {

    public User createUser(User user);

    public List<User> getAllUsers();

    public User getUserById(Long id) throws ChangeSetPersister.NotFoundException;

}

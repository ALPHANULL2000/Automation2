package com.example.automation2.dao;

import com.example.automation2.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*** CREATED: 8/13/2021 --- 10:42 PM --- ALPHANULL ***/
// Interface should implement in UserDAO class

@Component
public interface IUserDAO {
    int addUser(UUID uuid, final User user);
    Optional<User> getUserByUUID(final UUID uuid);
    List<User> getUserAll();
    int updateUser(final User user);
    int dropUser(final UUID uuid);
}



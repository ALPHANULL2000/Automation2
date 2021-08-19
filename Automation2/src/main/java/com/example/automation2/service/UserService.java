package com.example.automation2.service;

import com.example.automation2.dao.UserDAO;
import com.example.automation2.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*** Created: 8/14/2021 --- 9:12 PM --- ALPHANULL ***/
// Operate all UserDAO methods in Service layer (N-Layer) pattern

@Service
public class UserService {

    private final UserDAO dao;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public UserService(@NonNull UserDAO userDAO) {
        this.dao = userDAO;
    }

    public Optional<User> getUser(@NonNull UUID uuid) {
        if(dao.getUserByUUID(uuid).isPresent())
            return dao.getUserByUUID(uuid);
        else return Optional.empty();
    }

    public List<User> userList() {
        return dao.getUserAll();
    }

    public List<User> userListOrder(@NonNull String column, boolean isAsc) {
        return dao.getUserAllOrdered(column.trim(),isAsc);
    }

    public List<User> userListFilter(@NonNull String col, @NonNull String stt) {
        return dao.getUserAllFiltered(col.trim(),stt.trim());
    }

    public List<User> userListFilterNoCot(@NonNull String col, @NonNull String stt) {
        return dao.getUserAllFilteredNoCot(col.trim(),stt.trim());
    }

    public List<User> userListLiked(@NonNull String col, @NonNull String stt) {
        return dao.getUserAllLiked(col.trim(),stt.trim());
    }

    public List<User> userListComplex(@NonNull String sql) {
        return dao.getUserAllComplex(sql);
    }

    public int add(@NonNull UUID uuid, @NonNull User user) { return dao.addUser(uuid,user); }

    public int modify(@NonNull User user) {
        return dao.updateUser(user);
    }

    public int delete(@NonNull UUID uuid) {
        return dao.dropUser(uuid);
    }
}



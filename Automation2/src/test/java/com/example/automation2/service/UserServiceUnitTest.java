package com.example.automation2.service;

import com.example.automation2.dao.UserDAO;
import com.example.automation2.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/*** Created: 8/19/2021 --- 8:30 PM --- ALPHANULL ***/
// Unit Tests refers to UserService methods by JUnit5 (All passed)

class UserServiceUnitTest {

    @Mock private UserDAO dao;
    private UserService service;

    private User userTemp ;
    private UUID uuidTemp ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new UserService(dao);
        ApplicationContext context = new ClassPathXmlApplicationContext
                ("contexts/ApplicationContext.xml");
        userTemp = context.getBean("testUser",User.class);
        String uuidStr = context.getBean("testUserUUID",String.class);
        uuidTemp = UUID.fromString(uuidStr);
    }

    @AfterEach
    void tearDown() { }

    private void assertUserAttrs(@NonNull User user) {
        assertThat(user.getUuid()).isEqualTo(uuidTemp);
        assertThat(user.getUserName()).isEqualTo("UserName");
        assertThat(user.getUserAge()).isEqualTo(18);
        assertThat(user.getUserEmail()).isEqualTo("user@gmail.com");
        assertThat(user.getActive()).isTrue();
        assertThat(user.getUserGender()).isEqualTo(User.Gender.Other);
    }

    @Test
    void getUser() {
        given(dao.getUserByUUID(uuidTemp))
                .willReturn(Optional.of(userTemp));
        Optional<User> optionalUser = service.getUser(uuidTemp);
        assertThat(optionalUser.isPresent()).isTrue();
        assertUserAttrs(optionalUser.get());
    }

    @Test
    void userList() {
        List<User> listTemp = new ArrayList<>();
        listTemp.add(userTemp);
        given(dao.getUserAll()).willReturn(listTemp);
        List<User> allUsers = service.userList();
        assertThat(allUsers).hasSize(1);
        assertThat(allUsers).isNotEmpty();
        assertUserAttrs(allUsers.get(0));
    }

    @Test
    void add() {
        given(dao.getUserByUUID(uuidTemp)).willReturn(Optional.of(userTemp));
        given(dao.addUser(uuidTemp,userTemp)).willReturn(1);
        int resAdd = service.add(uuidTemp,userTemp);
        verify(dao).addUser(uuidTemp,userTemp);
        assertThat(resAdd).isEqualTo(1);
    }

    @Test
    void modify() {
        given(dao.getUserByUUID(uuidTemp)).willReturn(Optional.of(userTemp));
        given(dao.updateUser(userTemp)).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        int resultUpdate = service.modify(userTemp);
        verify(dao).updateUser(captor.capture());
        User userNew = captor.getValue();
        assertUserAttrs(userNew);
        assertThat(resultUpdate).isEqualTo(1);
    }

    @Test
    void delete() {
        given(dao.getUserByUUID(uuidTemp)).willReturn(Optional.of(userTemp));
        given(dao.dropUser(uuidTemp)).willReturn(1);
        int resultUpdate = service.delete(uuidTemp);
        verify(dao).dropUser(uuidTemp);
        assertThat(resultUpdate).isEqualTo(1);
    }
}



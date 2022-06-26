package com.finalproject.Repository;

import com.finalproject.Models.POJO.User;
import com.finalproject.helpers.DatabaseConnection;
import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.Assert;
import org.junit.Test;

public class UserRepositoryImplTest {
    private Injector injector = Guice.createInjector(new DatabaseConnection());
    private UserRepositoryImpl userRepository = injector.getInstance(UserRepositoryImpl.class);

    @Test
    public void testAddUserAndGetByUsername() {
        User user = new User("test1", "test1", "test1", "test1", "mail@gmai.com");
        userRepository.saveUser(user);

        User user2 = userRepository.getUserByUsername("test1");

        Assert.assertNotNull(userRepository.getUserByUsername("test1"));
        Assert.assertEquals(user, user2);
    }

    @Test
    public void testDeleteUser () {
        User user3 = new User("testDelete", "testDelete", "testDelete", "testDelete", "mail@gmai.com");
        userRepository.saveUser(user3);
        userRepository.deleteUser(user3);
        User user4 = userRepository.getUserByUsername("testDelete");
        Assert.assertNull(user4);
    }

    @Test
    public void testGetUserByUsername() {
        User user = new User("test1", "test1", "test1", "test1", "mail@gmai.com");
        userRepository.saveUser(user);
        User user2 = userRepository.getUserByUsername("test1");
        Assert.assertEquals(user, user2);
    }
}

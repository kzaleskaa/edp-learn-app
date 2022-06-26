package com.finalproject.Repository;

import com.finalproject.Models.POJO.User;

public interface UserRepository {
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String mail);
    User saveUser(User user);
    void deleteUser(User user);
}

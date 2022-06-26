package com.finalproject.config;

import com.finalproject.Models.POJO.User;

public class UserHolder {
    private User user;
    private final static UserHolder INSTANCE = new UserHolder();

    public UserHolder() {}

    public static UserHolder getInstance() {
        return INSTANCE;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public User getUser() {
        return this.user;
    }
}

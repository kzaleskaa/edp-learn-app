package com.finalproject.CustomEvents;

import com.finalproject.Models.LoginUser;
import javafx.event.Event;
import javafx.event.EventType;

public class LoginEvent extends Event {
    private LoginUser loginUser;

    public static final EventType<LoginEvent> LOGIN_USER = new EventType<>(Event.ANY, "ANY");
    public static final EventType<LoginEvent> ANY = LOGIN_USER;
    public static final EventType<LoginEvent> LOGIN_USER_SAVE = new EventType<>(LoginEvent.ANY, "LOGIN_USER_SAVE");


    public LoginEvent(EventType<? extends Event> eventType, LoginUser person) {
        super(eventType);
        this.loginUser = person;
    }

    public LoginUser getPerson () {
        return this.loginUser;
    }

}

package com.finalproject.CustomEvents;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Label;


public class RegisterMsgEvent extends Event {
    private Label msgLabel;

    public static final EventType<RegisterMsgEvent> NEW_MSG = new EventType<>(Event.ANY, "NEW_MSG");
    public static final EventType<RegisterMsgEvent> ANY = NEW_MSG;
    public static final EventType<RegisterMsgEvent> GET_NEW_MSG = new EventType<>(RegisterMsgEvent.ANY, "GET_NEW_MSG");

    public RegisterMsgEvent(EventType<? extends Event> eventType, Label msgLabel) {
        super(eventType);
        this.msgLabel = msgLabel;
    }

    public Label getMsgLabel() {
        return msgLabel;
    }

    public void setMsgLabel(Label msgLabel) {
        this.msgLabel = msgLabel;
    }
}

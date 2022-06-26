package com.finalproject.CustomEvents;

import javafx.concurrent.Task;

public class RegisterMsgTask extends Task<Void> {
    @Override
    protected Void call() throws Exception {
        Thread.sleep(1000);
        return null;
    }
}

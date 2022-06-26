package com.finalproject.CustomEvents;

import com.finalproject.CustomComponents.AlertDialog;
import com.google.common.eventbus.Subscribe;

public class EventListener {
    @Subscribe
    public void loginEventListener(LoginEvent event) {
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.setDialogTitle("WordsCard");
        alertDialog.setHeader("Hi " + event.getPerson().getUsername() + "!");
        alertDialog.setContentLabel("You have logged in successfully!\n It's a pleasure to see you again!");
        alertDialog.showDialogAlert();
    }
}


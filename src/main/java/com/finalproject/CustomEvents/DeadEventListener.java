package com.finalproject.CustomEvents;

import com.finalproject.CustomComponents.AlertDialog;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

public class DeadEventListener {
    @Subscribe
    public void deadEvent(DeadEvent event) {
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.setDialogTitle("WordCards");
        alertDialog.setContentLabel(event.getEvent().toString());
        alertDialog.showDialogAlert();
    }
}

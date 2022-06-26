package com.finalproject.CustomComponents;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class CustomLabel extends Label {
    private ArrayList<String> colors = new ArrayList<String>(Arrays.asList("#145DA0", "#0C2D48", "#2E8BC0", "#B1D4E0"));
    private int colorIndex = 0;

    public CustomLabel() {
        this("");
    }

    public CustomLabel(String text) {
        super(text);
        changeColorLabelText();
    }

    private void changeColorLabelText() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(colorIndex < colors.size()) {
                    setTextFill(Color.web(colors.get(colorIndex)));
                    colorIndex++;
                } else {
                    timer.cancel();
                    timer.purge();
                }
            }
        }, 0, 1000);
    }
}

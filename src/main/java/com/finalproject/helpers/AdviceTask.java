package com.finalproject.helpers;

import com.finalproject.Models.API.Advice;
import com.finalproject.Models.API.Slip;
import com.finalproject.config.PropertiesManager;
import com.google.gson.Gson;
import javafx.concurrent.Task;

import java.io.InputStreamReader;
import java.net.URL;

public class AdviceTask extends Task<String> {
    private final long limit;
    private String ADVICE_API_URL;

    public AdviceTask(long limit) {
        this.limit = limit;
        ADVICE_API_URL = PropertiesManager.getInstance().getProperty("ADVICE_API_URL");
    }

    @Override
    protected String call() throws Exception {
        Slip adviceText = null;
        long count = 0;
        while (count < limit) {
            adviceText = generateAdvice().getSlip();
            Thread.sleep(5000);
            count++;
            updateValue("#" + adviceText.getId() + ": " + adviceText.getAdvice());
        }
        return "#" + adviceText.getId() + ": " + adviceText.getAdvice();
    }

    private Advice generateAdvice() {
        URL url = null;
        try {
            url = new URL(ADVICE_API_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(url.openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Advice advice = new Gson().fromJson(reader, Advice.class);

        return advice;
    }
}

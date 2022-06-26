package com.finalproject.Models.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slip {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("advice")
    @Expose
    private String advice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Override
    public String toString() {
        return "Slip{" +
                "id=" + id +
                ", advice='" + advice + '\'' +
                '}';
    }
}

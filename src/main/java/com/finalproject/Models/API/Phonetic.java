package com.finalproject.Models.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phonetic {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("audio")
    @Expose
    private String audio;
    @SerializedName("sourceUrl")
    @Expose
    private String sourceUrl;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String toString() {
        return "Phonetic{" +
                "text='" + text + '\'' +
                ", audio='" + audio + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                '}';
    }
}

package com.finalproject.Models.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Word {
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("phonetic")
    @Expose
    private String phonetic;
    @SerializedName("meanings")
    @Expose
    private List<Meaning> meanings = null;
    @SerializedName("phonetics")
    @Expose
    private List<Phonetic> phonetics = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetic> phonetics) {
        this.phonetics = phonetics;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", meanings=" + meanings +
                ", phonetics=" + phonetics +
                '}';
    }
}

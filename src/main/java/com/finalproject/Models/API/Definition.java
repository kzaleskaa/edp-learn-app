package com.finalproject.Models.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Definition {
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("synonyms")
    @Expose
    private List<Object> synonyms = null;
    @SerializedName("antonyms")
    @Expose
    private List<Object> antonyms = null;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<Object> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Object> synonyms) {
        this.synonyms = synonyms;
    }

    public List<Object> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<Object> antonyms) {
        this.antonyms = antonyms;
    }

    public String toString() {
        return "Definition{" +
                "definition='" + definition + '\'' +
                ", synonyms=" + synonyms +
                ", antonyms=" + antonyms +
                '}';
    }
}

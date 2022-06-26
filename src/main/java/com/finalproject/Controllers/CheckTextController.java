package com.finalproject.Controllers;

import com.finalproject.CustomComponents.SearchButton;
import com.finalproject.Models.API.*;
import com.finalproject.config.PropertiesManager;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class CheckTextController implements Initializable{
    @FXML
    private TextField enteredWordTextField;
    @FXML
    private Label wordLabel;
    @FXML
    private  Label phoneticLabel;
    @FXML
    private Button playSoundButton;
    @FXML
    private Button searchButton;
    @FXML
    private ListView definitionListView;
    @FXML
    private Label msgLabel;
    @FXML
    private VBox wordHBox;
    @FXML
    private HBox searchHBox;

    private Word [] words;

    private final String wordAPI =  PropertiesManager.getInstance().getProperty("WORD_API");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SearchButton searchButton = new SearchButton();
        searchHBox.getChildren().add(searchButton);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    checkText(event);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void checkText(ActionEvent event) throws ExecutionException, InterruptedException {
        msgLabel.setText("Please wait...");
        CompletableFuture<Word> future = CompletableFuture.supplyAsync(() -> {
            String enteredWord = enteredWordTextField.getText();
            InputStreamReader reader = getWordDefinitionStreamReader(enteredWord);

            if (reader == null) {return null;}
            words = new Gson().fromJson(reader, Word[].class);
            return words;
        }).thenApply(word -> {
            Platform.runLater(() -> {
                if (word == null) {
                    wordNotFound();
                } else {
                    wordFound(word[0]);
                }

            });
            return word[0];
        });
    }

    private void wordNotFound() {
        msgLabel.setVisible(true);
        wordHBox.setVisible(false);
        msgLabel.setText("No results found");
    }

    private void wordFound(Word word) {
        setSoundUrl();
        msgLabel.setVisible(false);
        wordHBox.setVisible(true);
        wordLabel.setText(word.getWord());
        if(word.getPhonetic() != null) {
            phoneticLabel.setText(word.getPhonetic());
        } else {
            phoneticLabel.setText("No phonetic found");
        }
        setDefinitions();
    }

    private InputStreamReader getWordDefinitionStreamReader(String word) {
        URL url = null;
        try {
            url = new URL(wordAPI + word);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(url.openStream());
            return reader;
        } catch (IOException e) {
            System.out.println("No results found");
        }
        return null;
    }

    private void setSoundUrl() {
        List<Phonetic> phonetics = words[0].getPhonetics();
        String soundUrl = null;
        for (Phonetic phonetic : phonetics) {
            if(phonetic.getAudio() != null) {
                soundUrl = phonetic.getAudio();
            }
        }
        if(soundUrl != null && !soundUrl.isEmpty()) {
            playSoundButton.setVisible(true);
            playSoundButton.setDisable(false);
            String finalSoundUrl = soundUrl;
            playSoundButton.setOnAction(event -> {
                AudioClip audioClip = new AudioClip(finalSoundUrl);
                audioClip.play();
            });
        } else {
            playSoundButton.setDisable(true);
            playSoundButton.setVisible(false);
        }
    }

    private void setDefinitions() {
        List<Meaning> meanings = words[0].getMeanings();

        List<String> definitions = new ArrayList<>();

        for (Meaning meaning : meanings) {
            List<Definition> definitions1 = meaning.getDefinitions();
            for(Definition definition : definitions1) {
                definitions.add(meaning.getPartOfSpeech() +": "+definition.getDefinition());
            }
        }

        definitionListView.getItems().clear();
        definitionListView.getItems().addAll(definitions);
    }
}

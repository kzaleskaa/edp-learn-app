package com.finalproject.CustomComponents;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.FontWeight;

public class SearchButton extends Button {
    private ImageView imageView;

    public SearchButton() {
        super("Search");
        addImageView();
        updateStyles();
        registerListeners();
    }

    private void registerListeners() {
        setOnMouseEntered(event -> {stylesOnMouseEntered();});
        setOnMouseClicked(event -> {stylesOnMouseClicked();});
        setOnMouseExited(event -> {updateStyles();});
    }

    private void addImageView() {
        imageView = new ImageView(getClass().getResource("/images/icons/loupe.png").toExternalForm());
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        setGraphic(imageView);
    }

    private void updateStyles() {
        imageView.setVisible(true);
        setBackground(new Background(new BackgroundFill(Paint.valueOf("#add8e6"), null, null)));
        setTextFill(Paint.valueOf("#353535"));
        setFont(getFont().font("Verdana", 18));
        setStyle("-fx-border-radius: 5; -fx-border-color: #353535; -fx-pref-height: 1");
    }

    private void stylesOnMouseEntered() {
        setBackground(new Background(new BackgroundFill(Paint.valueOf("#3f51b5"), null, null)));
        setTextFill(Color.WHITE);
    }

    private void stylesOnMouseClicked() {
        imageView.setVisible(false);
        setBackground(new Background(new BackgroundFill(Paint.valueOf("#3f51b5"), null, null)));
        setTextFill(Color.WHITE);
        setFont(getFont().font("Verdana", FontWeight.BOLD, 18));
    }
}

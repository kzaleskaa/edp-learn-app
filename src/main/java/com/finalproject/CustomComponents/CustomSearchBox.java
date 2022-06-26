package com.finalproject.CustomComponents;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class CustomSearchBox extends HBox {
    private TextField input;
    private Button search;

    public CustomSearchBox() {
        getStylesheets().add(CustomSearchBox.class.getResource("/custom-controls-css/customCheckBox.css").toExternalForm());
        initGraphics();
    }

    private void initGraphics() {
        getStyleClass().add("custom-search-box");

        input = new TextField();
        input.setPromptText("Enter a word");

        search = new Button("Search");
        search.setOnAction(event -> {
            System.out.println(input.getText());
        });

        ImageView imageView = new ImageView(getClass().getResource("/images/icons/loupe.png").toExternalForm());
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        search.setGraphic(imageView);

        this.getChildren().addAll(input, search);
    }

    public String getText(){
        return input.getText();
    }

    public void setText(String value){
        input.setText(value);
    }


}

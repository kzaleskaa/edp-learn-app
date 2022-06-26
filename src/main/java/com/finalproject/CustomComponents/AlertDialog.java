package com.finalproject.CustomComponents;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertDialog {
    private Stage stage;
    private CustomLabel messageLabel;
    private Label contentLabel;
    private VBox vBox;
    private ImageView imageView;


    public AlertDialog() {
        stage = new Stage();
        contentLabel = new Label();
        messageLabel = new CustomLabel();
        imageView = new ImageView(getClass().getResource("/images/icons/hand.png").toExternalForm());
        vBox = new VBox();

        initGraphics();

        Scene scene = new Scene(vBox, 300, 400);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/flash-card.png")));
        stage.resizableProperty().setValue(Boolean.FALSE);
    }

    private void initGraphics() {
        messageLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setTextAlignment(TextAlignment.CENTER);

        contentLabel.setFont(contentLabel.getFont().font(18));
        contentLabel.setWrapText(true);
        contentLabel.setAlignment(Pos.CENTER);
        contentLabel.setTextAlignment(TextAlignment.CENTER);

        imageView.setFitHeight(200);
        imageView.setFitWidth(390);

        vBox.getChildren().addAll(messageLabel, contentLabel, imageView);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
    }

    public void setDialogTitle(String title) {
        stage.setTitle(title);
    }

    public void setHeader(String header) {
        messageLabel.setText(header);
    }

    public void setContentLabel(String title) {
        contentLabel.setText(title);
    }

    public void showDialogAlert() {
        stage.showAndWait();
    }
}

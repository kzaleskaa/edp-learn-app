package com.finalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomPreloaderController implements Initializable {
    @FXML
    private Label loadingLabel;
    public static Label label;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label = loadingLabel;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }
    private void sendNotificationToPreloader(){

    }

}

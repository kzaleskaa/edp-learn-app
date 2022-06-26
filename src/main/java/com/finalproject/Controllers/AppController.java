package com.finalproject.Controllers;

import com.finalproject.config.UserHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private AnchorPane contentAnchorPane;
    @FXML
    private Button logoutButton;

    private UserHolder userHolder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userHolder = UserHolder.getInstance();

        try {
            changeScene("/views/app/dashboard.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void dashboardOnAction(ActionEvent event) throws IOException {
        changeScene("/views/app/dashboard.fxml");
    }

    @FXML
    public void addNewCardOnAction(ActionEvent event) throws IOException {
        changeScene("/views/app/addNewCard.fxml");
    }

    @FXML
    public void checkTextOnAction(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/app/checkText.fxml"));
        root.getStylesheets().add("/custom-controls-css/customSearchBox.css");
        contentAnchorPane.getChildren().removeAll();
        contentAnchorPane.getChildren().setAll(root);
    }

    @FXML
    public void logoutOnAction(ActionEvent event) throws IOException {
        userHolder.setUser(null);

        Parent root = FXMLLoader.load(getClass().getResource("/views/auth/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }

    public void changeScene(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        contentAnchorPane.getChildren().removeAll();
        contentAnchorPane.getChildren().setAll(root);
    }
}

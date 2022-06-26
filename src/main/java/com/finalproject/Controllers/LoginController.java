package com.finalproject.Controllers;

import com.finalproject.CustomEvents.*;
import com.finalproject.Models.LoginUser;
import com.finalproject.Models.POJO.User;
import com.finalproject.Repository.UserRepositoryImpl;
import com.finalproject.config.InjectorHolder;
import com.finalproject.config.UserHolder;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


public class LoginController  {
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    private EventBus eventBus = new EventBus();
    private EventListener eventListener = new EventListener();
    private Injector injector = InjectorHolder.getInstance().getInjector();
    @Inject
    public UserRepositoryImpl userRepository = injector.getInstance(UserRepositoryImpl.class);

    @FXML
    public void initialize() {
        loginButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                String username = usernameTextField.getText();
                String password = passwordPasswordField.getText();

                LoginUser loginUser = new LoginUser(username, password);
                loginButton.fireEvent(new LoginEvent(LoginEvent.LOGIN_USER_SAVE, loginUser));
            }
        });

        loginButton.addEventHandler(LoginEvent.ANY, this::loginButtonOnAction);

        eventBus.register(eventListener);
        eventBus.register(new DeadEventListener());
    }

    @FXML
    private void loginButtonOnAction(LoginEvent event) {
        setLoginMessageLabel("Please wait, loading...", "INFORMATION");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() ->
                checkUserData(event.getPerson().getUsername())
        );

        CompletableFuture<Boolean> future = completableFuture.thenApply(validate -> {
            Platform.runLater(() -> {
                if(validate == null) {
                    eventBus.post(event);
                    loginUser(usernameExists(event.getPerson().getUsername()));
                } else {
                    setLoginMessageLabel("Bad credentials, try again or register.", "ERROR");
                }
            });
            return true;
        });
    }

    @FXML
    private void createAccountButtonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/auth/register.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/custom-controls-css/customCheckBox.css");
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String checkUserData(String username) {
        User user = usernameExists(username);
        return validateData(user);
    }

    private void loginScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/app/app.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.resizableProperty().setValue(Boolean.TRUE);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User usernameExists(String username) {
        User user = null;

        try {
            user = userRepository.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    private String validateData(User user) {
        String msg = null;

        if (checkEmptyFields()) {
            msg = "Please fill all fields!";
        } else if (checkPasswordAndUser(user)) {
            msg = "Bad credentials!";
        }

        return msg;
    }

    private boolean checkEmptyFields() {
        return usernameTextField.getText().isBlank() || passwordPasswordField.getText().isBlank();
    }

    private boolean checkPasswordAndUser(User user) {
        return user == null || !BCrypt.checkpw(passwordPasswordField.getText(), user.getPassword());
    }

    private void loginUser(User user) {
        UserHolder userHolder = UserHolder.getInstance();
        userHolder.setUser(user);
        loginScene();
    }

    private void setLoginMessageLabel(String msg, String type) {
        if (type == "ERROR") {
            loginMessageLabel.setTextFill(javafx.scene.paint.Color.RED);
        } else if (type == "SUCCESS") {
            loginMessageLabel.setTextFill(Color.GREEN);
        } else {
            loginMessageLabel.setTextFill(Color.GREY);
        }
        loginMessageLabel.setText(msg);
    }
}

package com.finalproject.Controllers;

import com.finalproject.CustomEvents.*;
import com.finalproject.Models.POJO.User;
import com.finalproject.Repository.UserRepositoryImpl;
import com.finalproject.config.InjectorHolder;
import com.finalproject.config.PropertiesManager;
import com.google.common.eventbus.EventBus;
import com.google.inject.Injector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private PasswordField confirmPasswordPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label msgRegistrationLabel;
    @FXML
    private CheckBox acceptRulesCheckBox;

    private EventBus eventBus = new EventBus();
    private EventListener eventListener = new EventListener();
    private Injector injector = InjectorHolder.getInstance().getInjector();
    private UserRepositoryImpl userRepository = injector.getInstance(UserRepositoryImpl.class);
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 465;
    private static final boolean SSL_FLAG = true;

    @FXML
    public void initialize(){
        RegisterMsgTask registerMsgTask = new RegisterMsgTask();
        registerMsgTask.addEventFilter(RegisterMsgEvent.ANY, this::welcomeText);

        registerMsgTask.setOnSucceeded( event -> {
            registerMsgTask.fireEvent(new RegisterMsgEvent(RegisterMsgEvent.GET_NEW_MSG, msgRegistrationLabel));
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(registerMsgTask);
        executorService.shutdown();

        eventBus.register(eventListener);
        eventBus.register(new DeadEventListener());
    }

    @FXML
    public void acceptRulesOnAction(ActionEvent event) {
        if(acceptRulesCheckBox.isSelected()) {
            registerButton.setDisable(false);
        } else {
            registerButton.setDisable(true);
        }
    }

    public void welcomeText(RegisterMsgEvent event) {
        event.getMsgLabel().setText("Create an account if you want to use the application.");
    }

    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/auth/login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void signUpButtonOnAction(ActionEvent event) throws ExecutionException, InterruptedException {
        setMessage("Please wait, loading...", "TRY_REGISTER");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() ->{
            String msg = validateUser();
                if(msg == null) {
                    try {
                        registerUser();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            return msg;
        });

        CompletableFuture<Boolean> future = completableFuture.thenApply(msg -> {
            Platform.runLater(() -> {
                if(msg != null) {
                    setMessage(msg, "ERROR");
                } else {
                    setMessage("User registered successfully!", "SUCCESS");
                    eventBus.post("You have registered successfully!\n Use your username and password to login.");
                }
            });
            return true;
        });
    }

    private String validateUser() {
        String msg = null;

        if(checkEmptyFields()) {
            msg = "Please fill all the fields.";
        } else if(usernameExists(usernameTextField.getText())) {
            msg = "Username already exists!";
        } else if (!isValidEmailAddress(emailTextField.getText())) {
            msg ="Enter valid email!";
        } else if (emailExists(emailTextField.getText())) {
            msg ="Email already exists!";
        } else if (!passwordMatch()) {
            msg ="Passwords do not match!";
        }

        return msg;
    }

    private void registerUser() throws MalformedURLException {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String mail = emailTextField.getText();
        String hashedPassword = generateHashPassword(passwordPasswordField.getText());

        User user = new User(username, firstName, lastName, hashedPassword, mail);

        saveNewUser(user);
        sendWelcomeMail(mail);

        clearInputs();
    }

    private void sendWelcomeMail(String emailAddress) throws MalformedURLException {
        String userName = PropertiesManager.getInstance().getProperty("MAIL_NAME");
        String password = PropertiesManager.getInstance().getProperty("MAIL_PASSWORD");

        URL url = getClass().getResource("/images/icons/students.png");

        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(HOST);
            String cid = email.embed(url, "Apache logo");
            String htmlMessage = "<div align=\"center\">"+
                                        "<h3>Hi "+ usernameTextField.getText()+"!</h3>"+
                                        "<img src=\"cid:"+cid+"\">"+
                                        "<p>You are now officially a member of our community!</p>"+
                                  "</div>";
            email.setSmtpPort(PORT);
            email.setAuthenticator(new DefaultAuthenticator(userName, password));
            email.setSSLOnConnect(SSL_FLAG);
            email.setFrom(userName);
            email.setSubject("Welcome in WordCards team!");
            email.setHtmlMsg(htmlMessage);
            email.setTextMsg("Your email client does not support HTML messages");
            email.addTo(emailAddress);
            email.send();
        } catch (Exception ex){
            System.out.println("Unable to send email");
            System.out.println(ex);
        }
    }

    private void saveNewUser(User user) {
        userRepository.saveUser(user);
    }

    private boolean usernameExists(String username) {
        try {
            userRepository.getUserByUsername(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean emailExists(String mail) {
        try {
            userRepository.getUserByEmail(mail);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkEmptyFields() {
        return firstNameTextField.getText().isEmpty() ||
                lastNameTextField.getText().isEmpty() ||
                usernameTextField.getText().isEmpty() ||
                passwordPasswordField.getText().isEmpty() ||
                confirmPasswordPasswordField.getText().isEmpty();
    }

    private boolean passwordMatch() {
        return passwordPasswordField.getText().equals(confirmPasswordPasswordField.getText());
    }

    private String generateHashPassword(String plainTextPassword) {
        String bcryptHashString = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(10));
        return bcryptHashString;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private void clearInputs() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        usernameTextField.clear();
        passwordPasswordField.clear();
        confirmPasswordPasswordField.clear();
        emailTextField.clear();
        acceptRulesCheckBox.setSelected(false);
    }

    private void setMessage(String msg, String type) {
        if (type == "ERROR") {
            msgRegistrationLabel.setTextFill(javafx.scene.paint.Color.RED);
        } else if (type == "SUCCESS") {
            msgRegistrationLabel.setTextFill(Color.GREEN);
        } else {
            msgRegistrationLabel.setTextFill(Color.GREY);
        }
        msgRegistrationLabel.setText(msg);
    }
}

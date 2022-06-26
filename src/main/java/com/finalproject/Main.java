package com.finalproject;

import com.finalproject.CustomComponents.CustomPreloader;
import com.finalproject.Repository.CardRepositoryImpl;
import com.finalproject.Repository.UserRepositoryImpl;
import com.finalproject.config.InjectorHolder;
import com.finalproject.config.UserHolder;
import com.finalproject.helpers.DatabaseConnection;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    private Injector injector;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/auth/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/flash-card.png")));
        stage.setTitle("WordCards");
        stage.setScene(scene);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        injector = Guice.createInjector(new DatabaseConnection());
        injector.getInstance(UserRepositoryImpl.class);
        injector.getInstance(CardRepositoryImpl.class);
        InjectorHolder injectorHolder = InjectorHolder.getInstance();
        injectorHolder.setInjector(injector);
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", CustomPreloader.class.getName());
        launch(args);
    }
}
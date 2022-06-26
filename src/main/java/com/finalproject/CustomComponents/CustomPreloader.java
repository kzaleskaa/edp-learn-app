package com.finalproject.CustomComponents;

import com.finalproject.Controllers.CustomPreloaderController;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomPreloader extends Preloader {
    private Stage stage;
    private Scene scene;

    public CustomPreloader() {}

    @Override
    public void init() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/custom/preloader.fxml"));
        scene = new Scene(root);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            CustomPreloaderController.label.setText(((ProgressNotification) info).getProgress() + "%");
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        switch(type) {
            case BEFORE_LOAD:
                break;
            case BEFORE_START:
                System.out.println("Before start");
                stage.hide();
                break;
        }
    }
}

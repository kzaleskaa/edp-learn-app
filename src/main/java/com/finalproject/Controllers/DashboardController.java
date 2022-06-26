package com.finalproject.Controllers;

import com.finalproject.Models.POJO.Card;
import com.finalproject.Repository.CardRepositoryImpl;
import com.finalproject.config.InjectorHolder;
import com.finalproject.config.UserHolder;
import com.finalproject.helpers.AdviceTask;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label randomAdviceLabel;
    @FXML
    private Label numberOfYourCards;
    @FXML
    private Label lastCardWord;
    @FXML
    private Label lastCardDescription;

    private Card lastCard;

    private Injector injector = InjectorHolder.getInstance().getInjector();
    private CardRepositoryImpl cardRepository = injector.getInstance(CardRepositoryImpl.class);


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            UserHolder userHolder = UserHolder.getInstance();
            lastCard = cardRepository.lastCard(userHolder.getUser().getId());
            welcomeLabel.setText("Welcome " + userHolder.getUser().getUsername() + "!");
            numberOfYourCards.setText(String.valueOf(cardRepository.numberOfUserCards(userHolder.getUser().getId())));
            if(lastCard != null) {
                lastCardWord.setText(lastCard.getWord());
                lastCardDescription.setText(lastCard.getDescription());
            } else {
                lastCardWord.setText("---");
                lastCardDescription.setText("You don't have any cards yet!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        invokeCounterTask();
    }

    private void invokeCounterTask() {
        AdviceTask task = new AdviceTask(10);
        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            randomAdviceLabel.setText(newValue);
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}

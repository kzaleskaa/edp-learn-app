package com.finalproject.Controllers;

import com.finalproject.Models.POJO.Card;
import com.finalproject.Repository.CardRepositoryImpl;
import com.finalproject.config.InjectorHolder;
import com.finalproject.config.UserHolder;
import com.google.inject.Injector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AddNewCardController implements Initializable {

    @FXML
    private TableColumn<Card, String> descriptionColumn;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private TableColumn<Card, String> exampleColumn;

    @FXML
    private TextArea exampleTextField;

    @FXML
    private TableColumn<Card, String> meaningColumn;

    @FXML
    private TextField meaningTextField;

    @FXML
    private TableColumn<Card, String> wordColumn;

    @FXML
    private TextField wordTextField;

    @FXML
    private TableView<Card> wordsTable;
    ArrayList <Card> cards;
    ObservableList<Card> cardsObservableList;

    private Injector injector;
    private CardRepositoryImpl cardRepository;
    private UserHolder userHolder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injector = InjectorHolder.getInstance().getInjector();
        cardRepository = injector.getInstance(CardRepositoryImpl.class);
        userHolder = UserHolder.getInstance();

        wordColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("word"));
        meaningColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("meaning"));
        exampleColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("example"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("description"));

        cards = cardRepository.getCardsByUserId(UserHolder.getInstance().getUser().getId());
        cardsObservableList = FXCollections.observableArrayList(cards);

        wordsTable.setItems(cardsObservableList);
    }

    @FXML
    void addNewCard(ActionEvent event) {
        String word = wordTextField.getText();
        String meaning = meaningTextField.getText();
        String example = exampleTextField.getText();
        String description = descriptionTextField.getText();

        if(!word.isEmpty()) {
            Card card = new Card(userHolder.getUser(), word, meaning, example, description);
            saveNewCard(card);
        }
    }
    
    @FXML
    private void deleteRowFromTable(ActionEvent event) {
        Card card = wordsTable.getSelectionModel().getSelectedItem();
        if(card != null) {
            cardRepository.deleteCard(card);
            cardsObservableList.remove(card);
        }
    }

    private void saveNewCard(Card card) {
        try {
            cardRepository.saveCard(card);
            cardsObservableList.add(card);
            wordsTable.setItems(cardsObservableList);
        } catch (Exception e) {
            getAlert("Error", "Error", "Error while adding new card");
        } finally {
            clearFields();
        }
    }

    private void getAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        wordTextField.clear();
        meaningTextField.clear();
        exampleTextField.clear();
        descriptionTextField.clear();
    }
}

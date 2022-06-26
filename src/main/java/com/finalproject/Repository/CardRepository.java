package com.finalproject.Repository;

import com.finalproject.Models.POJO.Card;

import java.util.ArrayList;

public interface CardRepository {
    ArrayList<Card> getCardsByUserId(Long id);
    void saveCard(Card card);
    void deleteCard(Card card);
    int numberOfUserCards(Long id);
    Card lastCard(Long id);
}

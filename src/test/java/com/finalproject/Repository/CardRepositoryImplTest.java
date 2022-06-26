package com.finalproject.Repository;

import com.finalproject.Models.POJO.Card;
import com.finalproject.Models.POJO.User;
import com.finalproject.helpers.DatabaseConnection;
import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class CardRepositoryImplTest extends TestCase {
    private Injector injector = Guice.createInjector(new DatabaseConnection());
    private UserRepositoryImpl userRepository = injector.getInstance(UserRepositoryImpl.class);
    private CardRepositoryImpl cardRepository = injector.getInstance(CardRepositoryImpl.class);

    @Test
    public void testSaveCard() {
        User user = new User("test1", "test1", "test1", "test1", "mail@gmail.com");
        userRepository.saveUser(user);
        Card card = new Card(user, "test1", "test1", "test1", "test1");
        cardRepository.saveCard(card);
        ArrayList<Card> cards = cardRepository.getCardsByUserId(user.getId());
        Assert.assertEquals(1, cards.size());
        Assert.assertEquals(card, cards.get(0));
    }

    @Test
    public void testDeleteCard() {
        User user = new User("test1", "test1", "test1", "test1", "mail@gmail.com");
        userRepository.saveUser(user);
        Card card = new Card(user, "test1", "test1", "test1", "test1");
        cardRepository.saveCard(card);
        cardRepository.deleteCard(card);
        ArrayList<Card> cards = cardRepository.getCardsByUserId(user.getId());
        Assert.assertEquals(0, cards.size());
    }

    @Test
    public void testGetCardsByUserId() {
        User user = new User("test1", "test1", "test1", "test1", "mail@gmail.com");
        userRepository.saveUser(user);
        Card card = new Card(user, "test1", "test1", "test1", "test1");
        cardRepository.saveCard(card);
        ArrayList<Card> cards = cardRepository.getCardsByUserId(user.getId());
        Assert.assertEquals(1, cards.size());
        Assert.assertEquals(card, cards.get(0));
        Assert.assertEquals(user.getId(), cards.get(0).getUser().getId());
    }

    @Test
    public void testNumberOfUserCards() {
        User user = new User("test1", "test1", "test1", "test1", "mail@gmail.com");
        userRepository.saveUser(user);
        Card card = new Card(user, "test1", "test1", "test1", "test1");
        cardRepository.saveCard(card);
        Assert.assertEquals(1, cardRepository.numberOfUserCards(user.getId()));
    }

    @Test
    public void testLastCard() {
        User user = new User("test1", "test1", "test1", "test1", "mail@gmail.com");
        userRepository.saveUser(user);
        Card card = new Card(user, "test1", "test1", "test1", "test1");
        cardRepository.saveCard(card);
        Assert.assertEquals(card, cardRepository.lastCard(user.getId()));
    }
}
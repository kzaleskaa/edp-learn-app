package com.finalproject.Repository;

import com.finalproject.Models.POJO.Card;
import com.google.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.ArrayList;

public class CardRepositoryImpl implements CardRepository, Serializable {
    private EntityManager em;

    @Inject
    public CardRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public ArrayList<Card> getCardsByUserId(Long id) {
        TypedQuery<Card> query = em.createQuery("SELECT c FROM Card c WHERE c.user.id = :id", Card.class);
        query.setParameter("id", id);
        return (ArrayList<Card>) query.getResultList();
    }

    @Override
    public void saveCard(Card card) {
        em.getTransaction().begin();
        em.persist(card);
        em.getTransaction().commit();
    }

    @Override
    public void deleteCard(Card card) {
        em.getTransaction().begin();
        em.remove(card);
        em.getTransaction().commit();
    }

    @Override
    public int numberOfUserCards(Long id) {
        TypedQuery<Card> query = em.createQuery("SELECT c FROM Card c WHERE c.user.id = :id", Card.class);
        query.setParameter("id", id);
        return query.getResultList().size();
    }

    @Override
    public Card lastCard(Long id) {
        try {
            TypedQuery<Card> query = em.createQuery("SELECT c FROM Card c WHERE c.user.id = :id ORDER BY c.id DESC", Card.class);
            query.setParameter("id", id);
            return query.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }
}

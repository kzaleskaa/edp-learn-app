package com.finalproject.helpers;

import com.finalproject.Repository.*;
import com.finalproject.config.PropertiesManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection extends AbstractModule {
    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE  = new ThreadLocal<EntityManager>();

    public void configure() {
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(CardRepository.class).to(CardRepositoryImpl.class);
    }

    @Provides @Singleton
    public EntityManagerFactory provideEntityManagerFactory() {
        Map<String, String> properties = new HashMap<String, String>();

        String DBASE_NAME = PropertiesManager.getInstance().getProperty("DBASE_NAME");
        String DBASE_URL = PropertiesManager.getInstance().getProperty("DBASE_URL");
        String DBASE_USERNAME = PropertiesManager.getInstance().getProperty("DBASE_USERNAME");
        String DBASE_PASSWORD = PropertiesManager.getInstance().getProperty("DBASE_PASSWORD");

        properties.put("jakarta.persistence.jdbc.driver", DBASE_NAME);
        properties.put("jakarta.persistence.jdbc.url", DBASE_URL);
        properties.put("jakarta.persistence.jdbc.user",  DBASE_USERNAME);
        properties.put("jakarta.persistence.jdbc.password", DBASE_PASSWORD);

        properties.put("hibernate.connection.pool_size", "10");

        return Persistence.createEntityManagerFactory("pu", properties);
    }

    @Provides
    public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = ENTITY_MANAGER_CACHE.get();

        if (entityManager == null) {
            ENTITY_MANAGER_CACHE.set(entityManager = entityManagerFactory.createEntityManager());
        }

        return entityManager;
    }

}

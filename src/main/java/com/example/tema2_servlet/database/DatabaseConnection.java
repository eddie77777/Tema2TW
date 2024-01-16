package com.example.tema2_servlet.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

@Getter
public class DatabaseConnection {
    EntityManager entityManager;

    public DatabaseConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("collegePersistenceUnit");
        this.entityManager = entityManagerFactory.createEntityManager();
    }
}

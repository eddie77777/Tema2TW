package com.example.tema2_servlet.database.dao;

import com.example.tema2_servlet.database.DatabaseConnection;
import com.example.tema2_servlet.database.model.PersonAccountEntity;
import com.example.tema2_servlet.database.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    DatabaseConnection databaseConnection = new DatabaseConnection();

    public PersonAccountEntity CanLogin(String username, String password, Role role){
        EntityManager em = databaseConnection.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PersonAccountEntity> cr = cb.createQuery(PersonAccountEntity.class);
        Root<PersonAccountEntity> root = cr.from(PersonAccountEntity.class);
        cr.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("username"), username));
        predicates.add(cb.equal(root.get("password"), password));
        predicates.add(cb.equal(root.get("personRole"), role));

        cr.where(predicates.toArray(new Predicate[]{}));
        boolean canLogin;
        List<PersonAccountEntity> list =em.createQuery(cr).getResultList();
        canLogin = !list.isEmpty();
        return canLogin ? list.get(0) : null;
    }
}

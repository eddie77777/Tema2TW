package com.example.tema2_servlet.database.dao;

import com.example.tema2_servlet.database.DatabaseConnection;
import com.example.tema2_servlet.database.model.CourseEntity;
import com.example.tema2_servlet.database.model.CourseInfo;
import com.example.tema2_servlet.database.model.PersonAccountEntity;
import com.example.tema2_servlet.database.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ProfessorAccountDao {
    DatabaseConnection databaseConnection = new DatabaseConnection();

    public List<CourseEntity> GetClassesTeachedBy(PersonAccountEntity personAccountEntity){
        EntityManager em = databaseConnection.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> cq =  cb.createQuery(CourseEntity.class);
        Root<CourseEntity> root = cq.from(CourseEntity.class);
        cq.select(root)
                .where(cb.equal(root.get("teacher").get("id"),personAccountEntity.getPersonInfo().getId()));

        List<CourseEntity> courses= em.createQuery(cq).getResultList();
        transaction.commit();
        return courses;
    }

    public List<CourseEntity> GetAllClasses(){
        EntityManager em = databaseConnection.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> cq =  cb.createQuery(CourseEntity.class);
        Root<CourseEntity> root = cq.from(CourseEntity.class);
        cq.select(root);
        List<CourseEntity> courses= em.createQuery(cq).getResultList();
        transaction.commit();
        return courses;
    }
}

package com.example.tema2_servlet.database.dao;

import com.example.tema2_servlet.database.DatabaseConnection;
import com.example.tema2_servlet.database.model.CourseEntity;
import com.example.tema2_servlet.database.model.GradeEntity;
import com.example.tema2_servlet.database.model.PersonAccountEntity;
import com.example.tema2_servlet.database.model.PersonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    DatabaseConnection databaseConnection = new DatabaseConnection();

    public List<PersonEntity> findAll(){
        EntityTransaction transaction = databaseConnection.getEntityManager().getTransaction();

        transaction.begin();
        List<PersonEntity> students = databaseConnection.getEntityManager()
                .createNativeQuery("SELECT * FROM person", PersonEntity.class).getResultList();
        transaction.commit();

        return students;
    }

    public PersonEntity GetStudentById(int id){
        EntityManager em = databaseConnection.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PersonEntity> cq =  cb.createQuery(PersonEntity.class);
        Root<PersonEntity> root = cq.from(PersonEntity.class);
        cq.select(root)
                .where(cb.equal(root.get("id"),id));

        PersonEntity student = em.createQuery(cq).getSingleResult();
        transaction.commit();
        return student;
    }

    public List<GradeEntity> GetGradesForCourse(PersonEntity student, int courseId){
        EntityManager em = databaseConnection.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GradeEntity> cq =  cb.createQuery(GradeEntity.class);
        Root<GradeEntity> root = cq.from(GradeEntity.class);
        

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("studentCourseInfo").get("student").get("id"),student.getId()));
        predicates.add(cb.equal(root.get("studentCourseInfo").get("course").get("id"), courseId));
        cq.where(predicates.toArray(new Predicate[]{}));

        List<GradeEntity> grades = em.createQuery(cq).getResultList();
        transaction.commit();
        return grades;
    }

}

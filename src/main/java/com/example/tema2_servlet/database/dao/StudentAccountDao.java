package com.example.tema2_servlet.database.dao;

import com.example.tema2_servlet.database.DatabaseConnection;
import com.example.tema2_servlet.database.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class StudentAccountDao {
    DatabaseConnection databaseConnection = new DatabaseConnection();

    public List<PersonAccountEntity> findAll(){
        EntityTransaction transaction = databaseConnection.getEntityManager().getTransaction();

        transaction.begin();
        List<PersonAccountEntity> students = databaseConnection.getEntityManager()
                .createNativeQuery("SELECT * FROM person_account where personrole=0", PersonAccountEntity.class).getResultList();
        transaction.commit();

        return students;
    }


    public static List<CourseInfo> GetClassesForStudent(PersonAccountEntity student){
        List<CourseInfo> list = student.getPersonInfo().getCourses();

        return list;
    }

}

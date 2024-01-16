package com.example.tema2_servlet.database.dao;

import com.example.tema2_servlet.database.DatabaseConnection;
import com.example.tema2_servlet.database.model.CourseEntity;
import com.example.tema2_servlet.database.model.CourseInfo;
import com.example.tema2_servlet.database.model.GradeEntity;
import com.example.tema2_servlet.database.model.PersonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jdk.jshell.PersistentSnippet;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.eclipse.persistence.jpa.jpql.parser.DateTimeFactory;

import javax.ejb.Local;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class GradeDAO {
    DatabaseConnection databaseConnection = new DatabaseConnection();


    public void AddGrade(PersonEntity student, int courseId, int grade){
        EntityManager em = databaseConnection.getEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {

            transaction.begin();
            LocalDateTime localDateTime = LocalDateTime.now();
            Query qry = em.createNativeQuery("INSERT INTO grade(datetime, grade, person_id, course_id) VALUES (?, ?, ?, ?);");
            qry.setParameter(1, localDateTime);
            qry.setParameter(2, grade);
            qry.setParameter(3, student.getId());
            qry.setParameter(4, courseId);
            qry.executeUpdate();

//            em.refresh(student);
            transaction.commit();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void FinnishFinalGrades(int courseId) {
        EntityManager em = databaseConnection.getEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            CourseDao courseDao = new CourseDao();
            StudentDao studentDao = new StudentDao();
            List<Long> ids = courseDao.GetStudentIdsForCourse(courseId);
            for (long studentId : ids) {
                List<GradeEntity> grades = studentDao.GetGradesForCourse(studentDao.GetStudentById((int) studentId), courseId);
                Optional<Integer> sum = grades.stream().map(g -> g.getGrade()).reduce((a, b) -> a + b);
                if (sum.isPresent()) {
                    double average = sum.get() * 1.0 / grades.size();
                    Query qry = em.createNativeQuery("UPDATE person_course SET " +
                            "final_grade=? WHERE course_id=? AND person_id=?");
                    qry.setParameter(1, average);
                    qry.setParameter(2, courseId);
                    qry.setParameter(3, studentId);
                    qry.executeUpdate();
                }
            }
//            em.getEntityManagerFactory().getCache().evictAll();

            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void refreshGrades(List<CourseEntity> courses){
        EntityManager em = databaseConnection.getEntityManager();
        for(CourseEntity course: courses)
            em.refresh(course);
    }
}

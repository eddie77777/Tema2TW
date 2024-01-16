package com.example.tema2_servlet.database.dao;

import com.example.tema2_servlet.database.DatabaseConnection;
import com.example.tema2_servlet.database.model.CourseEntity;
import com.example.tema2_servlet.database.model.PersonAccountEntity;
import com.example.tema2_servlet.database.model.PersonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class CourseDao {
    DatabaseConnection databaseConnection = new DatabaseConnection();

    public CourseEntity GetCourseById(int id){
        EntityManager em = databaseConnection.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> cq =  cb.createQuery(CourseEntity.class);
        Root<CourseEntity> root = cq.from(CourseEntity.class);
        cq.select(root)
                .where(cb.equal(root.get("id"),id));

        CourseEntity course = em.createQuery(cq).getSingleResult();
        transaction.commit();
        return course;
    }
    public String[] GetCourseNameAndDescriptionById(int id){
        EntityManager em = databaseConnection.getEntityManager();
        Query qry = em.createNativeQuery("SELECT c.course_name, c.course_description FROM course c WHERE id=?");
        qry.setParameter(1, id);
        try {
            Object[] course = (Object[]) qry.getSingleResult();
            return new String[]{(String)course[0], (String)course[1]};

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Long> GetStudentIdsForCourse(int id){
        EntityManager em = databaseConnection.getEntityManager();
        Query qry = em.createNativeQuery("SELECT c.person_id FROM person_course c WHERE course_id=?");
        qry.setParameter(1, id);
        try {
            return qry.getResultList();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean AddCourse(PersonAccountEntity teacher, List<PersonAccountEntity> students,
                             String course_name, String course_descriprion){
        EntityManager em = databaseConnection.getEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {

        transaction.begin();

        Query qry = em.createNativeQuery("INSERT INTO course(course_description, course_name, teacher_id) VALUES (?, ?, ?);");
        qry.setParameter(1, course_descriprion);
        qry.setParameter(2, course_name);
        qry.setParameter(3, teacher.getPersonInfo().getId());
        qry.executeUpdate();
//        long course_id = (long) qry.getSingleResult();

        qry = em.createNativeQuery("SELECT id from course where course_description=? and course_name=? and teacher_id=?");
        qry.setParameter(1, course_descriprion);
        qry.setParameter(2, course_name);
        qry.setParameter(3, teacher.getPersonInfo().getId());
        int course_id = (int) qry.getSingleResult();

        for(PersonAccountEntity student : students){
            Query qry1 = em.createNativeQuery("INSERT INTO person_course(final_grade, course_id, person_id) VALUES (null, ?, ?);");
            qry1.setParameter(1, course_id);
            qry1.setParameter(2, student.getPersonInfo().getId());
            qry1.executeUpdate();
        }

        transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean UpdateCourse(List<PersonAccountEntity> new_students,
                             String course_name, String course_descriprion, int courseId){
        EntityManager em = databaseConnection.getEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {

        transaction.begin();

        Query qry = em.createNativeQuery("UPDATE course SET " +
                "course_description=?, course_name=? WHERE id=?");
        qry.setParameter(1, course_descriprion);
        qry.setParameter(2, course_name);
        qry.setParameter(3, courseId);
        qry.executeUpdate();
//        long course_id = (long) qry.getSingleResult();

        List<Long> old_students = GetStudentIdsForCourse(courseId);
        for(PersonAccountEntity student : new_students){
            if (!old_students.contains(student.getPersonInfo().getId())) {
                Query qry1 = em.createNativeQuery("INSERT INTO person_course(final_grade, course_id, person_id) VALUES (null, ?, ?);");
                qry1.setParameter(1, courseId);
                qry1.setParameter(2, student.getPersonInfo().getId());
                qry1.executeUpdate();
            }
        }

        for(Long student_id : old_students){
            if (new_students.stream().noneMatch((student) -> student.getPersonInfo().getId() == student_id)) {
                Query qry1 = em.createNativeQuery("DELETE FROM grade WHERE course_id=? AND person_id=?");
                qry1.setParameter(1, courseId);
                qry1.setParameter(2, student_id);
                qry1.executeUpdate();

                qry1 = em.createNativeQuery("DELETE FROM person_course WHERE course_id=? AND person_id=?");
                qry1.setParameter(1, courseId);
                qry1.setParameter(2, student_id);
                qry1.executeUpdate();
            }
        }

            transaction.commit();

        CourseEntity course = GetCourseById(courseId);
        em.refresh(course);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean DeleteCourse(int courseId){
        EntityManager em = databaseConnection.getEntityManager();

        EntityTransaction transaction = em.getTransaction();
        try {

            transaction.begin();

            //TODO: Trebuia sa pun delete in cascada :(
            Query qry = em.createNativeQuery("DELETE FROM grade " +
                    "WHERE course_id=?");
            qry.setParameter(1, courseId);
            qry.executeUpdate();

            qry = em.createNativeQuery("DELETE FROM person_course " +
                    "WHERE course_id=?");
            qry.setParameter(1, courseId);
            qry.executeUpdate();

            qry = em.createNativeQuery("DELETE FROM course " +
                    "WHERE id=?");
            qry.setParameter(1, courseId);
            qry.executeUpdate();

            transaction.commit();

        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public void refreshCourse(CourseEntity course) {
        EntityManager em = databaseConnection.getEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();

    }
}

package com.example.tema2_servlet;

import com.example.tema2_servlet.database.dao.CourseDao;
import com.example.tema2_servlet.database.dao.GradeDAO;
import com.example.tema2_servlet.database.dao.ProfessorAccountDao;
import com.example.tema2_servlet.database.dao.StudentAccountDao;
import com.example.tema2_servlet.database.model.CourseEntity;
import com.example.tema2_servlet.database.model.PersonAccountEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "home", value = "/home")
public class HomeServlet extends HttpServlet {

    List<CourseEntity> courses;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username =  (String) req.getSession().getAttribute("username");
        if(username == null){
            resp.sendRedirect("/login");
            return;
        }

        boolean isStudent =  (boolean) req.getSession().getAttribute("isStudent");
        req.setAttribute("username", username);
        req.setAttribute("isStudent", isStudent);
        if(isStudent) {
            PersonAccountEntity connectedStudent = (PersonAccountEntity) req.getSession().getAttribute("student");
            req.setAttribute("courses", StudentAccountDao.GetClassesForStudent(connectedStudent));
            req.getRequestDispatcher("/home.jsp").forward(req, resp);
        }
        else{
            PersonAccountEntity connectedTeacher = (PersonAccountEntity) req.getSession().getAttribute("teacher");
            ProfessorAccountDao professorAccountDao = new ProfessorAccountDao();

//            courses = professorAccountDao.GetClassesTeachedBy(connectedTeacher);
            courses = professorAccountDao.GetAllClasses();
            Boolean sorted = (Boolean) req.getSession().getAttribute("sorted");
            if(sorted == Boolean.TRUE)
                courses.sort(Comparator.comparing(CourseEntity::getCourseName));
            req.setAttribute("courses", courses);
            req.setAttribute("connectedTeacher", connectedTeacher.getPersonInfo().getId());

            req.getRequestDispatcher("/home-teacher.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String buttonValue = req.getParameter("buttonSortNames");
        if(buttonValue!=null && !buttonValue.isEmpty()) {
            Long id = Long.parseLong ( buttonValue.split("_")[1]);
            List<Long> idsToSort = (List<Long>) req.getSession().getAttribute("idsToSort");
            if(idsToSort == null)
                idsToSort = new ArrayList<>();
            if(idsToSort.stream().anyMatch(i -> i.equals(id)))
                idsToSort.removeIf(el -> Objects.equals(el, id));
            else
                idsToSort.add(id);
            req.getSession().setAttribute("idsToSort", idsToSort);
//            courses.sort(Comparator.comparing(CourseEntity::getCourseName));
            resp.sendRedirect("/home");

        }


        buttonValue = req.getParameter("buttonSort");
        if(buttonValue!=null && !buttonValue.isEmpty()) {
            Boolean sortedNow = (Boolean) req.getSession().getAttribute("sorted");
            if(sortedNow == Boolean.TRUE)
                req.getSession().setAttribute("sorted", false);
            else
                req.getSession().setAttribute("sorted", true);

            courses.sort(Comparator.comparing(CourseEntity::getCourseName));
            resp.sendRedirect("/home");

        }

        // edit course was clicked
        buttonValue = req.getParameter("buttonEditCourse");
        if(buttonValue!=null && !buttonValue.isEmpty()) {
            req.getSession().setAttribute("course_id", buttonValue);
//        resp.sendRedirect("/edit-course?course_id="+ URLEncoder.encode(buttonValue, "UTF-8"));
            resp.sendRedirect("/edit-course");
        }
        else{ // delete

            buttonValue = req.getParameter("buttonDeleteCourse");
            if(buttonValue!=null && !buttonValue.isEmpty()){
                Integer courseId = Integer.parseInt(buttonValue);
                CourseDao courseDao = new CourseDao();
                courseDao.DeleteCourse(courseId);
                resp.sendRedirect("/home");
            }
            else {
                buttonValue = req.getParameter("buttonGrades");
                if(buttonValue!=null && !buttonValue.isEmpty()){
                    String[] values = buttonValue.split("_");
                    Integer courseId = Integer.parseInt(values[0]);
                    Integer studentId = Integer.parseInt(values[1]);
                    req.getSession().setAttribute("courseId", courseId);
                    req.getSession().setAttribute("studentId", studentId);
                    resp.sendRedirect("/grades");
                }
                else{
                    buttonValue = req.getParameter("buttonFinalGrades");
                    if(buttonValue!=null && !buttonValue.isEmpty()) {
                        Integer courseId = Integer.parseInt(buttonValue);
                        GradeDAO gradeDAO = new GradeDAO();
                        CourseDao courseDao = new CourseDao();
                        CourseEntity course = courseDao.GetCourseById(courseId);
                        gradeDAO.FinnishFinalGrades(courseId);
                        courseDao.refreshCourse(course);
                        resp.sendRedirect("/home");

                    }

                }
            }

        }
        //buttoGrades
    }
}

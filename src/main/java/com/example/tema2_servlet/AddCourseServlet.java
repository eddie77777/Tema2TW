package com.example.tema2_servlet;

import com.example.tema2_servlet.database.dao.CourseDao;
import com.example.tema2_servlet.database.dao.StudentAccountDao;
import com.example.tema2_servlet.database.model.PersonAccountEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "addCourse", value = "/add-course")
public class AddCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username =  (String) req.getSession().getAttribute("username");
        StudentAccountDao studentAccountDao = new StudentAccountDao();
        if(username == null){
            resp.sendRedirect("/login");
            return;
        }
        List<PersonAccountEntity> students = studentAccountDao.findAll();
        req.getSession().setAttribute("students_available", students);
        req.getSession().setAttribute("action_type", "Add");
        req.getRequestDispatcher("/add-course.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StudentAccountDao studentAccountDao = new StudentAccountDao();
        List<PersonAccountEntity> students = studentAccountDao.findAll();
        List<PersonAccountEntity> students_for_course = new ArrayList<>();

        for (PersonAccountEntity student: students){
            String checkboxValue = req.getParameter("chkName"+student.getId());
            if ("selected".equals(checkboxValue)) {
                students_for_course.add(student);
            }
        }

        CourseDao courseDao = new CourseDao();
        PersonAccountEntity teacher = (PersonAccountEntity) req.getSession().getAttribute("teacher");
        String course_name = req.getParameter("course-name");
        String course_description = req.getParameter("course-description");
        boolean could = courseDao.AddCourse(teacher, students_for_course,course_name, course_description);
        resp.sendRedirect("/home");
    }
}

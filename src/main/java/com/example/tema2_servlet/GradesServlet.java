package com.example.tema2_servlet;

import com.example.tema2_servlet.database.dao.GradeDAO;
import com.example.tema2_servlet.database.dao.StudentAccountDao;
import com.example.tema2_servlet.database.dao.StudentDao;
import com.example.tema2_servlet.database.model.CourseEntity;
import com.example.tema2_servlet.database.model.GradeEntity;
import com.example.tema2_servlet.database.model.PersonEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "grades", value = "/grades")
public class GradesServlet  extends HttpServlet {
    private PersonEntity currentStudent;
    private Integer currentCourseId;
    private Integer currentStudentId;
    List<GradeEntity> gradesForCurrentCourse;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String username =  (String) req.getSession().getAttribute("username");
        StudentAccountDao studentAccountDao = new StudentAccountDao();
        if(username == null){
            resp.sendRedirect("/login");
            return;
        }
        currentCourseId = (Integer) req.getSession().getAttribute("courseId");
        currentStudentId = (Integer) req.getSession().getAttribute("studentId");

        if(currentCourseId == null || currentStudentId == null){
            resp.sendRedirect("/home");
            return;
        }
//        req.getSession().removeAttribute("courseId");
//        req.getSession().removeAttribute("studentId");

        StudentDao studentDao = new StudentDao();
        currentStudent = studentDao.GetStudentById(currentStudentId);

        gradesForCurrentCourse = studentDao.GetGradesForCourse(currentStudent, currentCourseId);
        Boolean sorted = (Boolean) req.getSession().getAttribute("sorted_"+currentCourseId);
        if(sorted == Boolean.TRUE)
            gradesForCurrentCourse.sort(Comparator.comparing(GradeEntity::getDateTime));
        else
            gradesForCurrentCourse.sort(Comparator.comparing(GradeEntity::getGrade));

        req.setAttribute("grades", gradesForCurrentCourse);
        req.setAttribute("student_name", currentStudent.getName());
        req.getRequestDispatcher("/grades.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String buttonValue = req.getParameter("buttonSortG");
        if(buttonValue!=null && !buttonValue.isEmpty()) {
            Long id = Long.parseLong ( buttonValue.split("_")[1]);

            Boolean sortedNow = (Boolean) req.getSession().getAttribute("sorted_"+currentCourseId);
            if(sortedNow == Boolean.TRUE)
                req.getSession().setAttribute("sorted_"+currentCourseId, false);
            else
                req.getSession().setAttribute("sorted_"+currentCourseId, true);

            resp.sendRedirect("/grades");
            return;
        }

        Integer grade = Integer.parseInt(req.getParameter("grade"));
        GradeDAO gradeDAO = new GradeDAO();
        gradeDAO.AddGrade(currentStudent, currentCourseId, grade);

        StudentDao studentDao = new StudentDao();
        currentStudent = studentDao.GetStudentById(currentStudentId);

         resp.sendRedirect("/home");

    }
}

package com.example.tema2_servlet;

import com.example.tema2_servlet.database.dao.AccountDao;
import com.example.tema2_servlet.database.dao.StudentAccountDao;
import com.example.tema2_servlet.database.model.PersonAccountEntity;
import com.example.tema2_servlet.database.model.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(name = "/login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.isNull(req.getSession().getAttribute("username"))) {
            try {
                if(Objects.isNull(req.getAttribute("error")))
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                else
                {
                    PrintWriter out = resp.getWriter();
                    out.println("<html><body>");
                    out.println("<h1>" + req.getAttribute("error") + "</h1>");
                    out.println("</body></html>");
                }
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
        else {
//            try {
                resp.sendRedirect("/home");
//                req.getRequestDispatcher("/home.jsp").forward(req, resp);
//            } catch (ServletException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[INFO]: Entered Post Student Login Servlet");
        String name =(String)req.getParameter("username");
        String password =(String)req.getParameter("password");
        System.out.println("I received the name: " + name);

        AccountDao accountDao = new AccountDao();
        PersonAccountEntity student = accountDao.CanLogin(name, password, Role.STUDENT);

        if(student != null){
            req.getSession().setAttribute("username", name);
            req.getSession().setAttribute("isStudent", true);
            req.getSession().setAttribute("student", student);
        }
        else{
            PersonAccountEntity teacher = accountDao.CanLogin(name, password, Role.TEACHER);
            if(teacher!= null){
                req.getSession().setAttribute("username", name);
                req.getSession().setAttribute("isStudent", false);
                req.getSession().setAttribute("teacher", teacher);
            }
            else
                req.setAttribute("error", "login error");
        }
        this.doGet(req, resp);

    }

    @Override
    public void destroy() {

    }
}

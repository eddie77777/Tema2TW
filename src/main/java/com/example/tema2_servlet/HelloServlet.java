package com.example.tema2_servlet;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html");
//
//        // Hello
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>" + message + "</h1>");
//        out.println("</body></html>");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");

//        StudentEntity studentEntity = new StudentEntity();
//        studentEntity.setName("Alex");
//
//        StudentAccountEntity studentAccountEntity = new StudentAccountEntity();
//        studentAccountEntity.setUsername("alex");
//        studentAccountEntity.setPassword("pass");
//        studentAccountEntity.setStudentInfo(studentEntity);

//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("collegePersistenceUnit");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(studentEntity);
//        entityManager.persist(studentAccountEntity);
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        entityManagerFactory.close();


    }

    public void destroy() {
    }
}
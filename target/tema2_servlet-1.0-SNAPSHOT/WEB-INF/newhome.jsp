<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="https://example.com/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <style>
        div {
            padding-left: 75px;
            padding-right: 75px;
            padding-top: 15px;
        }
    </style>
</head>
<body>
<div>
    <h1>Hello, ${username}! </h1>

    <div id="cours">
        <button class="btn btn-primary" onclick="location.href='/add-course'" name="btnAddCourse">Add Course</button>
        <button class="btn btn-danger" onclick="location.href='/logout'" name="btnLogout">Logout</button>
        <br>
        <h>Here are your Courses:</h>

        <form method="POST">
            <button class="btn btn-info" type="submit" name="buttonSort" value="sort">Sort</button>
        </form>

        <table class="table">
            <thead class="thead-light">
            <tr>
                <th scope="col">Course</th>
                <th scope="col">Student</th>
                <th scope="col">Info</th>
                <th scope="col">Final grade</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${courses}" var="courseEntity">
                <c:forEach items="${courseEntity.students}" var="courseInfo">
                    <tr>
                        <td>${courseEntity.courseName}</td>
                        <td>${courseInfo.student.name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${courseEntity.teacher.id == connectedTeacher}">
                                    <button class="btn btn-info" name="buttonGrades" value="${courseEntity.id}_${courseInfo.student.id}">Grades</button>
                                </c:when>
                                <c:otherwise>You don't have rights</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty courseInfo.finalGrade}">
                                    Not available
                                </c:when>
                                <c:otherwise>
                                    ${courseInfo.finalGrade}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${courseEntity.teacher.id == connectedTeacher}">

                                <form method="POST">
                                        <%--                    onclick="location.href='/edit-course' --%>
                                    <button class="btn btn-success" id="btn${courseEntity.id}" name="buttonEditCourse" value="${courseEntity.id}">Edit Course</button>
                                    <button class="btn btn-danger" id="btn${courseEntity.id}" name="buttonDeleteCourse" value="${courseEntity.id}">Delete Course</button>
                                    <button class="btn btn-warning" id="btn${courseEntity.id}" name="buttonFinalGrades" value="${courseEntity.id}">Calculate Final Grades</button>

                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:forEach>

            </tbody>
        </table>

    </div>
</div>

<%-- jQuery Bootstrap --%>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>

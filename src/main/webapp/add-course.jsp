<%--
  Created by IntelliJ IDEA.
  User: sorana
  Date: 1/6/2024
  Time: 8:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${action_type} Course</title>
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
<h1>${action_type} a course:</h1>
<form  method="POST">
    <label for="course-name">Course Name:</label><br>
    <input placeholder="Course Name" name="course-name" id="course-name" value="${courseName}" required></input><br>
    <label for="course-description">Course Description:</label><br>
    <input placeholder="Course Description" name="course-description" id="course-description" value="${courseDescription}" required></input><br>

    <h3 style="margin-top: 15px">Add Students</h3>
    <div class="form-check">
        <br>
        <c:forEach items="${students_available}" var="student">

            <c:choose>
                <c:when test="${empty selected_students_ids}">
                    <input type="checkbox" class="form-check-input" name="chkName${student.id}" id="chkId${student.id}" value="selected">
                </c:when>
                <c:otherwise>
                    <c:if test="${selected_students_ids.contains(student.personInfo.id)}">
                        <input type="checkbox" class="form-check-input" name="chkName${student.id}" id="chkId${student.id}" value="selected" checked>
                    </c:if>
                    <c:if test="${not selected_students_ids.contains(student.personInfo.id)}">
                        <input type="checkbox" class="form-check-input" name="chkName${student.id}" id="chkId${student.id}" value="selected">
                    </c:if>

                </c:otherwise>
            </c:choose>

            <label class="form-check-label" for="chkId${student.id}">
                    ${student.username}
            </label>
            <br>
        </c:forEach>
    </div>
    <button type="submit" class="btn btn-secondary" style="margin-top: 14px" name="btnSaveCourse">Save</button>
</form>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>

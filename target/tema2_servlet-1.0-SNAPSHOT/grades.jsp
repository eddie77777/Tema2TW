<%--
  Created by IntelliJ IDEA.
  User: sorana
  Date: 1/8/2024
  Time: 1:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Grades</title>
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
    <h1> Grades for student ${student_name} :) </h1>
    <p>The grades are:</p>
    <form method="POST">
        <button class="btn btn-info" type="submit" name="buttonSortG" value="sort_${courseId}">Sort</button>
    </form>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">Grade</th>
            <th scope="col">Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${grades}" var="gradeVal">
            <tr>
                <td>${gradeVal.grade}</td>
                <td>${gradeVal.dateTime}</td>
            </tr>
        </c:forEach>
        </tbody>

    </table>

    <form method="POST">
        <label>Add Grade:</label>
        <input placeholder="Grade" name="grade" id="grade"></input><br>
        <button type="submit" class="btn btn-success" id="btnAddGrade" name="buttonAddGrade" >Add Grade</button>
    </form>

</div>

<%-- jQuery Bootstrap --%>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>

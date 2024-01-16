<%--
  Created by IntelliJ IDEA.
  User: sorana
  Date: 1/2/2024
  Time: 7:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <button class="btn btn-danger" onclick="location.href='/logout'">Logout</button>

<c:choose>
    <c:when test="${isStudent}">

<div id="grades">
    <h>Here are your Courses:</h>

    <c:forEach items="${courses}" var="courseInfo">
        <div class="card-header">
            <h5 class="mb-0">
                <button class="btn btn-light" data-toggle="collapse" data-target="#collapse${courseInfo.course.id}" aria-expanded="false" aria-controls="collapseOne">
                    ${courseInfo.course.courseName}
                </button>
            </h5>
        </div>

        <div id="collapse${courseInfo.course.id}" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
            <div class="card-body">
                <table class="table">
                    <thead class="thead-light">
                        <tr>
                            <th scope="col">Grade</th>
                            <th scope="col">Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${courseInfo.grades}" var="gradeVal">
                            <tr>
                                <td>${gradeVal.grade}</td>
                                <td>${gradeVal.dateTime}</td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>

                <p>Your final grade for this class is:
                <c:choose>
                    <c:when test="${empty courseInfo.finalGrade}">
                        Not available
                    </c:when>
                    <c:otherwise>
                         ${courseInfo.finalGrade}
                    </c:otherwise>
                </c:choose>
                </p>

            </div>
        </div>
    </c:forEach>


</div>
</div>
</c:when>
<c:otherwise>
    <p> You are a teacher </p>
</c:otherwise>
</c:choose>

<%-- jQuery Bootstrap --%>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>

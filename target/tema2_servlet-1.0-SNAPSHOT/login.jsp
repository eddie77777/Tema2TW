<%--
  Created by IntelliJ IDEA.
  User: sorana
  Date: 1/2/2024
  Time: 7:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <style>
        div {
            padding-left: 75px;
            padding-right: 75px;
            padding-top: 75px;
        }
    </style>

</head>
<body>
<div>
<h1>Login </h1>

<form  method="POST">
    <label>Username:</label><br>
    <input placeholder="Username" name="username" id="username"></input><br>
    <label>Password:</label><br>
    <input type="password" placeholder="Password" name="password" id="password"></input><br>
    <button type="submit" class="btn btn-secondary" style="margin-top: 14px" name="buttonLogin">Login</button>
</form>
</div>
</body>
</html>

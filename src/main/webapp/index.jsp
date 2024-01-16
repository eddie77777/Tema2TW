<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
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
<h1 ><%= "Hello World!" %>
</h1>
<br/>
<%--<a href="/hello-servlet">Hello Servlet</a>--%>
    <a class="badge badge-primary" href="/login">Login</a>
</div>
</body>
</html>
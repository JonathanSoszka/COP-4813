<%-- 
    Document   : registration
    Created on : Oct 7, 2020, 7:20:11 PM
    Author     : Jonathan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="/DndBuddy/resources/styles/global.css" />
    </head>
    <body>
        <div class="container">
            <h1>Registration Page</h1>
            <div class="row">
                <div class="col">Already A Member?</div>
                <a href="/DndBuddy/login/login.jsp"><button class="btn btn-primary">Login</button></a>
            </div>
            <form action="LoginController" method="POST" class="form">
                <div class="form-group">
                    <label for="">Username</label>
                    <input type="text" name="username" class="form-control">
                </div>
                <div class="form-group">
                    <label for="">Password</label>
                    <input type="password" name="password" class="form-control">
                </div>
                <button type="submit" name="buttonName" value="register" class="btn btn-primary">Register</button>
            </form>
            
        </div>
    </body>
</html>

<%-- 
    Document   : dashboard
    Created on : Oct 19, 2020, 3:30:37 AM
    Author     : Wyatt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="/DndBuddy/resources/styles/global.css" />
    </head>
    </head>
    <body>
        <jsp:include page="/WEB-INF/components/navbar.jsp"/>
        <h1>Dashboard</h1>
        <p>Logged In User: ${user.username}</p>
    </body>
</html>

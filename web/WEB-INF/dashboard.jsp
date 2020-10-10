<%-- 
    Document   : dashboard
    Created on : Oct 9, 2020, 2:15:57 AM
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
    </head>
    <body>
        <jsp:include page="/WEB-INF/components/navbar.jsp"/>
        <h1>Hello World! Dashboard</h1>
        <p>${user.username}</p>
    </body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
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
    <div class="container mt-5 character-container p-0">
        <core:forEach var="note" items="${notes}">
            <div class="row">
                <div id="${note.id}" class="mb-3 col-6 offset-3 p-0 character-card">
                    <div class="pl-2 shadow-lg">
                        <span class="pr-2">${note.text}</span>
                        <span class="float-right pl-2">CREATOR: ${note.author}</span>
                    </div>
                </div>        
            </div>
        </core:forEach>
        <div class="character-card-controls">
            <a href="notes/edit"><button class="btn btn-sharp btn-primary btn-new-note shadow-lg">Edit notes</button></a> 
        </div>
    </div>
</body>
</html>

<script>
    function confirmDelete()
    {
        return confirm("Do you really want to delete this note?");
    }
</script>

<style>
    .btn-edit-notes{
        position: fixed;
        right:    15px;
        bottom:   15px;
    }
</style>

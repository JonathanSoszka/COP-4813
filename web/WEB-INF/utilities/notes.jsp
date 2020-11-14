<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="/DndBuddy/resources/styles/global.css" />
        <script src="https://kit.fontawesome.com/815f235d4b.js" crossorigin="anonymous"></script>
    </head>
</head>
<body>
    <jsp:include page="/WEB-INF/components/navbar.jsp"/>
    <div class="container mt-5 p-0">
        <div class="row character-title mb-5">
            <div class="col d-flex justify-content-between">
                <h1>My Notes</h1>
            </div>
        </div>
        <div class="row mb-4">
            <div class="col d-flex justify-content-end">
                <a href="notes/edit"><button class="btn btn-sharp btn-primary btn-new-note shadow-lg">Edit notes</button></a> 
            </div>
        </div>
        <core:forEach var="note" items="${notes}">
            <div class="row">
                <div id="${note.id}" class="mb-3 col-10 offset-1 p-0">
                    <div class="shadow-sm">
                        <div class="card">
                            <div class="card-body">
                                ${note.text}
                            </div>
                        </div>
                    </div>
                </div>        
            </div>
        </core:forEach>
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

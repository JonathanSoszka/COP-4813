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
        <form method="POST" action="">
            <core:forEach var="note" items="${notes}">
                <div class="row">
                    <div name="note-${note.id}" id="${note.id}" class="mb-3 col-6 offset-3 p-0 pl-2 shadow-lg character-card">
                        <textarea name="text-${note.id}">${note.text}</textarea>
                        <div style="float:right;">
                            <core:if test = "${note.vis == 'public'}">
                                <input type="radio" name="vis-${note.id}" value="public" label="Public" checked>Public
                                <input type="radio" name="vis-${note.id}" value="private" label="Private">Private
                            </core:if>
                            <core:if test = "${note.vis == 'private'}">
                                <input type="radio" name="vis-${note.id}" value="public" label="Public">Public
                                <input type="radio" name="vis-${note.id}" value="private" label="Private" checked>Private
                            </core:if>
                        </div>
                        <br>
                    </div>
                    <input id="delete-note-${note.id}" hidden>
                    <button onclick="deleteNote(${note.id})" name="method" value="deleteNote" class="ml-2 mb-3 p-0 btn btn-sharp btn-secondary btn-new-note shadow-lg" >Delete</button>
                </div>
            </core:forEach>
            
            

            <div class="container col-6 character-card-controls">
                <button name="method" value="addNote" class="btn btn-sharp btn-primary btn-new-note shadow-lg">Add note</button>
                <button name="method" value="saveNotes" class="fixed-bottom float-right btn btn-sharp btn-primary btn-new-note shadow-lg">Save</button>
            </div>
        </form>
    </div>
</body>
</html>

<script>
    function deleteNote(noteId)
    {
        if (confirm("Do you really want to delete this note?")) {
            document.getElementById("delete-note-"+noteId).name="deleteId";
            document.getElementById("delete-note-"+noteId).value=noteId;
            return;
        }
    }
</script>

<style>
    .btn-edit-notes{
        position: fixed;
        right:    15px;
        bottom:   15px;
    }
</style>

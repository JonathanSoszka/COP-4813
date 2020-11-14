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

        <div class="container mt-5 p-0">
            <div class="row character-title mb-5">
                <div class="col d-flex justify-content-between">
                    <h1>Editing Notes</h1>
                </div>
            </div>
        <form method="POST" action="">
            <input id="delete-field" name="deleteId" value=0 hidden>
            <core:forEach var="note" items="${notes}">
                <div class="row">
                    <div name="note-${note.id}" id="${note.id}" class="mb-3 col-8 offset-1 p-0">
                        <div class="card">
                            <div class="card-body">
                                <textarea class="w-100" name="text-${note.id}">${note.text}</textarea>
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
                        </div>

                    </div>
                    <div class="col-2">
                        <button name="method" value="deleteNote" class="btn btn-outline-secondary" onclick="(deleteNote('${note.id}'))">
                            <i class="fas fa-trash"></i>
                        </button>
                </div>
                </div>
            </core:forEach>
            <div class="container col-8 offset-1 p-0 character-card-controls">
                <button name="method" value="addNote" class="btn btn-sharp btn-primary shadow btn-block">Add note</button>
            </div>
            <button name="method" value="saveNotes" class="btn btn-sharp btn-primary btn-edit-notes shadow-lg">Save</button>
        </form>
    </div>
           
</body>
</html>

<script>
    function deleteNote(noteId)
    {
        console.log(noteId);
        document.getElementById('delete-field').value=noteId;
    }
</script>

<style>
    .btn-edit-notes{
        position: fixed !important;
        right:    15px !important;
        bottom:   15px !important;
    }
</style>

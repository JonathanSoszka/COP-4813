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
        <core:forEach var="character" items="${characters}">
            <div class="row">
                <div class="mb-3 col-6 offset-3 p-0 character-card">
                    <div class="pl-2 shadow-lg">
                        <span class="font-weight-bold" style="font-size: 28px;">${character.name}</span>
                        <br>
                        <span>Level 5 | ${character.race} | ${character.characterClass}</span>
                    </div>

                    <div class="character-card-controls">
                        <a href="characters/detail?id=${character.id}"><button class="btn btn-sharp btn-primary w-50 float-left">View</button></a> 
                        <form action="" method="POST" id="delete-form" onsubmit="return confirmDelete(`${character.name}`)">
                            <input hidden type="text" name="id" value="${character.id}">
                            <button name="method" value="deleteCharacter"class="btn btn-sharp btn-danger w-50 float-right">Delete</button>   
                        </form>
                    </div>
                </div>        
            </div>
        </core:forEach>
    </div>
    <a href="characters/create"><button class="btn btn-sharp btn-primary btn-new-char sahdow-lg">New Character</button></a> 
</body>
</html>

<script>
    function confirmDelete(name)
    {
        return confirm("Do you really want to delete " + name + "?");
    }
</script>


<style>
    .character-card{
        outline: solid 1px;
    }

    .btn-new-char{
        position: fixed;
        right:    15px;
        bottom:   15px;
    }
</style>

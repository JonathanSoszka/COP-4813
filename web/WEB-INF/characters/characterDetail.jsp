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
        <h1>Character Detail Page</h1>
        <p>${character.name}</p>
        <p>Level ${character.level} | ${character.race} | ${character.characterClass}</p>
        <a href="/DndBuddy/characters">Back</a>
    </body>
</html>

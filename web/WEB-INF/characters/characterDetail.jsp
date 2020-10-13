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
    <div class="container">
        <a href="/DndBuddy/characters">Back</a>
        <div class="row character-title">
            <div class="col d-flex justify-content-between">
                <h1>${character.name}</h1>
                <a href="edit?id=${character.id}">
                    <button class="btn btn-sharp btn-primary h-75" style="bottom:1px; right:2px; position:absolute;">Edit</button>
                </a>
            </div>
        </div>
        <div class="container detail-container">
            <div class="mt-5 row content-divider">
                <div class="col character-title"><h2>Traits</h2></div>
            </div>
            <div class="mt-5 row">
                <div class="col-3">
                    Race: ${character.race}
                </div>
                <div class="col-3">
                    Class: ${character.characterClass}
                </div>
                <div class="col-3">
                    Alignment: ${character.alignment}
                </div>
                <div class="col-3">
                    Background: ${character.background}
                </div>
            </div>
            <div class="mt-5 row content-divider">
                <div class="col character-title"><h2>Stats</h2></div>
            </div>
            <div class="row mt-5">
                <div class="col-4">
                    Level: ${character.level}
                </div>
                <div class="col-4">
                    Health: ${character.hp} / ${character.maxHp}
                </div>
            </div>

            <div class="mt-5 row">
                <div class="col-4">
                    Strength: ${character.strength}
                </div>
                <div class="col-4">
                    Dexterity: ${character.dexterity}
                </div>
                <div class="col-4">
                    Constitution: ${character.constitution}
                </div>
            </div>
            <div class="row mt-5">
                <div class="col-4">
                    Intelligence: ${character.intelligence}
                </div>
                <div class="col-4">
                    Wisdom: ${character.wisdom}
                </div>
                <div class="col-4">
                    Charisma: ${character.charisma}
                </div>
            </div>

            <div class="mt-5 row content-divider">
                <div class="col character-title"><h2>Capmaigns</h2></div>
            </div>
            <div class="mt-5 row">
                <div class="col-3">
                    TODO
                </div>
            </div>
        </div>
    </div>


</body>
<style>
    .character-title{
        border-bottom: solid 3px black
    }
</style>
</html>

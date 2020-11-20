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
    <div class="container">
        <a href="/DndBuddy/characters">Back</a>
        <div class="row character-title">
            <div class="col d-flex justify-content-between">
                <h1>Editing ${character.name}</h1>
                <button onclick="saveChanges()" class="btn btn-sharp btn-primary h-75" style="bottom:1px; right:2px; position:absolute;">Save Changes</button>
            </div>
        </div>
        <div class="container detail-container">
            <div class="mt-5 row content-divider">
                <div class="col character-title"><h2>Traits</h2></div>
            </div>
            <form id="character-form" method="POST">
            <input name="method" value="saveCharacter" hidden>
            <input name="id" value="${character.id}" hidden>

            <input name="name" value="${character.name}" hidden>
            <div class="mt-5 row">
                <div class="col-3">
                    <div class="form-group">
                        <label for="">Race</label>
                        <select name="race" class="form-control">
                            <core:forEach var="race" items="${races}">
                                <option ${race.equals(character.race)? "selected" : ""} value="${race}">${race}</option>
                            </core:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-3">
                    <div class="form-group">
                        <label for="">Class</label>
                        <select name="characterClass" class="form-control">
                            <core:forEach var="characterClass" items="${classes}">
                                <option ${characterClass.equals(character.characterClass)? "selected" : ""} value="${characterClass}">${characterClass}</option>
                            </core:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-3">
                    <div class="form-group">
                        <label for="">Alignment</label>
                        <select name="alignment" class="form-control">
                            <core:forEach var="alignment" items="${alignments}">
                                <option ${alignment.equals(character.alignment)? "selected" : ""} value="${alignment}">${alignment}</option>
                            </core:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-3">
                    <div class="form-group">
                        <label for="">Backgrounds</label>
                        <select name="background" class="form-control">
                            <core:forEach var="background" items="${backgrounds}">
                                <option ${background.equals(character.background)? "selected" : ""} value="${background}">${background}</option>
                            </core:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="mt-5 row content-divider">
            <div class="col character-title"><h2>Stats</h2></div>
        </div>
        <div class="row mt-5">
            <div class="col-4">
                Level:
                <input type="text" class="stat-input" name="level" value="${character.level}">
            </div>
            <div class="col-4">
                Health:
                <input type="text" class="health-input" name="hp" value="${character.hp}" maxlength="4"> /
                <input type="text" class="health-input" name="maxHp" value="${character.maxHp}" maxlength="4">
            </div>
        </div>

        <div class="mt-5 row">
            <div class="col-4">
                Strength:
                <input type="text" class="stat-input" name="strength" value="${character.strength}">
            </div>
            <div class="col-4">
                Dexterity:
                <input type="text" class="stat-input" name="dexterity" value="${character.dexterity}">
            </div>
            <div class="col-4">
                Constitution:
                <input type="text" class="stat-input" name="constitution" value="${character.constitution}">
            </div>
        </div>
        <div class="row mt-5">
            <div class="col-4">
                Intelligence:
                <input type="text" class="stat-input" name="intelligence" value="${character.intelligence}">
            </div>
            <div class="col-4">
                Wisdom:
                <input type="text" class="stat-input" name="wisdom" value="${character.wisdom}">
            </div>
            <div class="col-4">
                Charisma:
                <input type="text" class="stat-input" name="charisma" value="${character.charisma}">
            </div>
        </div>
    </form>
    </div>
</div>


</body>

<script>
    function saveChanges() {
        if (window.confirm("Are you sure you want to save these changes?"))
            document.getElementById('character-form').submit();
    }

</script>

<style>
    .character-title{
        border-bottom: solid 3px black
    }

    .health-input{
        max-width: 50px;
    }
    
    .stat-input{
        max-width: 30px;
        float:right;
        margin-right: 170px;
    }
</style>
</html>

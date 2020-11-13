<%-- Document : campaignDetail Created on : Nov 12, 2020, 4:01:37 PM Author :
Jonathan --%> <%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>JSP Page</title>
  </head>
  <body>
    <jsp:include page="/WEB-INF/components/navbar.jsp" />

    <div class="container">
      <a href="/DndBuddy/campaigns">Back</a>
      <div class="row title">
        <div class="col d-flex justify-content-between">
          <h1>${campaign.name}</h1>
        </div>
      </div>

      <div class="container mt-5 character-container p-0">
        <core:forEach var="character" items="${campaign.characters}">
            <div class="row">
                <div class="mb-3 col-6 offset-3 p-0 character-card">
                    <div class="pl-2 shadow-lg">
                        <span class="font-weight-bold" style="font-size: 28px;">${character.name}</span>
                        <br>
                        <span>Level ${character.level} | ${character.race} | ${character.characterClass}</span>
                    </div>

                    <div class="character-card-controls">
                        <form action="" method="POST" id="delete-form" onsubmit="return confirmDelete(`${character.name}`)">
                            <input hidden type="text" name="characterId" value="${character.id}">
                            <button name="method" value="removeCharacter"class="btn btn-sharp btn-danger w-100 float-right">Remove</button>   
                        </form>
                    </div>
                </div>        
            </div>
        </core:forEach>
    </div>

    </div>
    <a href="addCharacter"><button class="btn btn-sharp btn-primary btn-new-char sahdow-lg">Add Character To Campaign</button></a> 
  </body>
</html>

<script>
    function confirmDelete(name)
    {
        return confirm("Do you really want to remove " + name + "?");
    }
</script>

<style>
  .title {
    border-bottom: solid 3px black;
  }

  .character-card{
        outline: solid 2px;
    }

    .btn-new-char{
        position: fixed;
        right:    15px;
        bottom:   15px;
    }
</style>

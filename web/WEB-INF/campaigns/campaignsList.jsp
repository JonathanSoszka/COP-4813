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
    <div class="container mt-5 campaign-container p-0">
        <div class="row character-title mb-5">
            <div class="col d-flex justify-content-between">
                <h1>My Campaigns</h1>
            </div>
        </div>
        <core:forEach var="campaign" items="${campaigns}">
            <div class="row">
                <div class="mb-3 col-6 offset-3 p-0 campaign-card">
                    <div class="pl-2 shadow-lg">
                        <span class="font-weight-bold" style="font-size: 28px;">${campaign.name}</span>
                        <br>
                        <span>Players: ${campaign.characters.size()}</span>
                    </div>

                    <div class="campaign-card-controls">
                        <a href="campaigns/detail?id=${campaign.id}"><button class="btn btn-sharp btn-primary w-50 float-left">View</button></a> 
                        <form action="" method="POST" id="delete-form" onsubmit="return confirmDelete(`${campaign.name}`)">
                            <input hidden type="text" name="id" value="${campaign.id}">
                            <button name="method" value="deleteCampaign"class="btn btn-sharp btn-danger w-50 float-right">Delete</button>   
                        </form>
                    </div>
                </div>        
            </div>
        </core:forEach>
    </div>
    <a href="campaigns/create"><button class="btn btn-sharp btn-primary btn-new-char sahdow-lg">New Campaign</button></a> 
</body>
</html>

<script>
    function confirmDelete(name)
    {
        return confirm("Do you really want to delete " + name + "?");
    }
</script>


<style>
    .campaign-card{
        border: solid 2px;
        border-radius: 7px;
    }

    .btn-new-char{
        position: fixed;
        right:    15px;
        bottom:   15px;
    }

    .character-title{
        border-bottom: solid 3px black;
    }
</style>

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
        <h1 class="mb-5">Add Character to ${campaign.name}</h1>
        <div class="row">
            <div class="col">
                <P class="text-danger">${error}</p>
                <form class="form" action="" method="POST">
                    <div class="form-group">
                        <label for="">Username</label>
                        <input class="form-control" name="username" placeholder="Username" type="text">
                    </div>
                    <div class="form-group">
                        <label for="">Character Name</label>
                        <input class="form-control" name="characterName" placeholder="Character Name" type="text">
                    </div>
                    <button type="submit" name="method" value="addCharacter" class="btn btn-sharp btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </div>

</body>
</html>



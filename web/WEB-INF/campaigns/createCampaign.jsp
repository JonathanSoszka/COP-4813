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
    <h1>Campaign Creation</h1>
    <div class="container">
        <div class="row">
            <div class="col">
                <form class="form" action="" method="POST">
                    <div class="form-group">
                        <label for="">Campaign Name</label>
                        <input class="form-control" name="name" placeholder="Name" type="text">
                    </div>
                    <button type="submit" name="method" value="createCampaign" class="btn btn-sharp btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </div>

</body>
</html>



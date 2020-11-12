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
    <h1>Character Creation</h1>
    <div class="container">
        <div class="row">
            <div class="col">
                <form class="form" action="" method="POST">
                    <div class="form-group">
                        <label for="">Character Name</label>
                        <input class="form-control" name="name" placeholder="Name" type="text">
                    </div>
                    <div class="form-group">
                        <label for="">Race</label>
                        <select name="race" class="form-control">
                            <core:forEach var="race" items="${races}">
                                <option value="${race}">${race}</option>
                            </core:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="">Class</label>
                        <select name="characterClass" class="form-control">
                            <core:forEach var="characterClass" items="${classes}">
                                <option>${characterClass}</option>
                            </core:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="">Alignment</label>
                        <select name="alignment" class="form-control">
                            <core:forEach var="alignment" items="${alignments}">
                                <option>${alignment}</option>
                            </core:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="">Background</label>
                        <select name="background" class="form-control">
                            <core:forEach var="background" items="${backgrounds}">
                                <option>${background}</option>
                            </core:forEach>
                        </select>
                    </div>
                    <button type="submit" name="method" value="createCharacter" class="btn btn-sharp btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </div>

</body>
</html>



<%-- Document : registration Created on : Oct 7, 2020, 7:20:11 PM Author :
Jonathan --%> <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>JSP Page</title>
    <link rel="stylesheet" href="/DndBuddy/resources/styles/global.css" />
  </head>
  <body>
    <div class="container">
      <h1>Login Page</h1>
      <div class="row">
        <div class="col">New User?</div>
        <a href="/DndBuddy/register">
          <button class="btn btn-primary">Register</button></a
        >
      </div>
      <form action="" method="POST" class="form">
        <div class="form-group">
          <label for="">Username</label>
          <input type="text" name="username" class="form-control" />
        </div>
        <div class="form-group">
          <label for="">Password</label>
          <input type="password" name="password" class="form-control" />
        </div>
        <div>
          <p class="text-danger">${loginError}</p>
        </div>
        <button
          type="submit"
          name="login"
          value="1"
          class="btn btn-primary"
        >
          Login
        </button>
      </form>
    </div>
  </body>
</html>

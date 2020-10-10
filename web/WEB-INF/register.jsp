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
      <div class="row">
        <div class="col col-8 offset-2">
          <div class="card mt-5 shadow">
            <div class="card-header text-center" style="font-size: 20px;">DnD Buddy</div>
            <div class="card-body">
              <form action="" method="POST" class="form">
                <div class="form-group">
                  <label for="">Username</label>
                  <input type="text" name="username" value="${helper.data.username}" class="form-control" />
                  <p class="text-danger">${helper.errors.username}</p>
                </div>
                <div class="form-group">
                  <label for="">Password</label>
                  <input type="password" name="password" value="${helper.data.password}" class="form-control" />
                  <p class="text-danger">${helper.errors.password}</p>
                </div>
                <div class="form-group">
                  <label for="">Confirm Password</label>
                  <input
                    type="password"
                    name="confirmPassword"
                    class="form-control"
                    value="${helper.data.confirmPassword}"
                  />
                  <p class="text-danger">${helper.errors.confirmPassword}</p>
                  <p class="text-danger">${helper.errors.confirmPasswordMatch}</p>
                </div>
                <button
                  type="submit"
                  name="register"
                  value="1"
                  class="btn btn-primary w-100"
                >
                  Register
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div class="row mt-2">
        <div class="col-8 offset-2 d-flex align-items-center flex-column">
          <div class="block">Already A Member? </div>
          <div>
          <a class="block" href="/DndBuddy/login"
            >Login</button></div>
        </div>
      </div>
    </div>
  </body>
</html>

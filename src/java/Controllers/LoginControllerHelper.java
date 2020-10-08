package Controllers;

import Entities.User;
import Helpers.AuthHelper;
import Helpers.HibernateHelper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import shared.ButtonMethod;

public class LoginControllerHelper extends ControllerHelperBase {

    public LoginControllerHelper(HttpServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response) {
        super(servlet, request, response);
    }

    @Override
    public void doPost() throws ServletException, IOException {
        switch (request.getParameter("buttonName")) {
            case "register":
                registerMethod();
            case "login":
                loginMethod();
            default:
                break;
        }
    }

    public void registerMethod() throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User newUser = new User();
        newUser.setUsername(username);

        try {
            newUser.updatePassword(password);
        } catch (NoSuchAlgorithmException ex) {
            //swallow
        }

        HibernateHelper.updateDB(newUser);
        response.sendRedirect("/DndBuddy/login/login.jsp");
    }

    public void loginMethod() throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        Object userFromDb = HibernateHelper.getFirstMatch(user, "username", username);
        if (userFromDb != null) {
            user = (User) userFromDb;
        } else {
            //do something
            return;
        }

        String passwordHash = AuthHelper.getSecurePassword(password, user.getPasswordSalt());
        if (user.getPasswordHash().equals(passwordHash)) {
            //success
            response.sendRedirect("/DndBuddy/");
        } else {
            //do something
            return;
        }
    }
}

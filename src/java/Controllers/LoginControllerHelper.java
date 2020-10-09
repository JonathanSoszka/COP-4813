package Controllers;

import Entities.User;
import Helpers.AuthHelper;
import Helpers.HibernateHelper;
import com.cedarsoftware.util.io.JsonWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginControllerHelper extends ControllerHelperBase {

    @Override
    public void doGet() throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    public void doPost() throws ServletException, IOException {
        String buttonName = request.getParameter("buttonName");
        switch (buttonName) {
            case "register":
                registerMethod();
                break;
            case "login":
                loginMethod();
                break;
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

    public void loginMethod() throws IOException, ServletException {
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
            String jsonStr = JsonWriter.objectToJson(user);
            Cookie userCookie = new Cookie("user", jsonStr);
            userCookie.setPath("/");
            response.addCookie(userCookie);

            response.sendRedirect("/DndBuddy");

        } else {
            //do something
            return;
        }
    }
}

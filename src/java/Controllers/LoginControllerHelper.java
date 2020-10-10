package Controllers;

import Entities.User;
import Helpers.AuthHelper;
import Helpers.HibernateHelper;
import com.cedarsoftware.util.io.JsonWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import shared.ButtonMethod;

public class LoginControllerHelper extends ControllerHelperBase {

    @Override
    public void doGet() throws ServletException, IOException {
        forwardToJsp("login.jsp");
    }

    @Override
    public void doPost() throws ServletException, IOException {
        request.setAttribute("helper", this);
        executeButtonMethod();
    }

    @ButtonMethod(buttonName = "login", isDefault = true)
    public void loginMethod() throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean success = false;

        User user = new User();
        Object userFromDb = HibernateHelper.getFirstMatch(user, "username", username);
        if (userFromDb != null) {
            user = (User) userFromDb;
            String passwordHash = AuthHelper.getSecurePassword(password, user.getPasswordSalt());
            if (user.getPasswordHash().equals(passwordHash)) {
                success = true;
                response.addCookie(objectToCookie(user, "user", "/"));
            }
        }
        if (success) {
            redirectToController("dashboard");
        } else {
            request.setAttribute("loginError", "Invalid Username or Password.");
            forwardToJsp("login.jsp");
        }
    }
}

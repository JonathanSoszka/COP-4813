package Controllers;

import DTO.LoginForm;
import DTO.UserDTO;
import Entities.User;
import Helpers.AuthHelper;
import Helpers.HibernateHelper;
import Helpers.HttpGet;
import Helpers.HttpPost;
import com.cedarsoftware.util.io.JsonWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import shared.ButtonMethod;

public class LoginControllerHelper extends ControllerHelperBase {

    @HttpGet(isDefault = true)
    public void getLoginView() throws ServletException, IOException {
        if (getFromSession("user") != null) {
            redirectToController("");
        } else {
            forwardToJsp("login.jsp");
        }
    }

    @HttpPost(method = "login")
    public void login() throws IOException, ServletException {
        LoginForm data = new LoginForm();
        fillObjectFromRequest(data);
        request.setAttribute("data", data);

        if (!isValid(data)) {
            forwardToJsp("login.jsp");
        }

        boolean success = false;
        User user = new User();
        user = (User) HibernateHelper.getFirstMatch(user, "username", data.getUsername());
        if (user != null) {
            String passwordHash = AuthHelper.getSecurePassword(data.getPassword(), user.getPasswordSalt());
            if (user.getPasswordHash().equals(passwordHash)) {
                success = true;
                addToSession("user", user);
                setCookie("user", UserDTO.mapFromEntity(user));
            }
        }
        if (success) {
            redirectToController("");
        } else {
            request.setAttribute("loginError", "Invalid Username or Password.");
            forwardToJsp("login.jsp");
        }
    }

    @HttpPost(method = "logout")
    public void logout() throws IOException {
        removeCookie("user");
        removeFromSession("user");
        redirectToController("login");
    }
}

package Controllers;

import DTO.LoginForm;
import Entities.User;
import Helpers.AuthHelper;
import Helpers.HibernateHelper;
import com.cedarsoftware.util.io.JsonWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import shared.ButtonMethod;

public class LoginControllerHelper extends ControllerHelperBase {

    private LoginForm data;

    public LoginForm getData() {
        return data;
    }

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
        request.setAttribute("helper", this);
        data = LoginForm.buildFromRequest(request);
        
        if(!isValid(data)){
            forwardToJsp("login.jsp");
        }
        
        String g = data.getPassword();

        boolean success = false;

        User user = new User();
        Object userFromDb = HibernateHelper.getFirstMatch(user, "username", data.getUsername());
        if (userFromDb != null) {
            user = (User) userFromDb;
            String passwordHash = AuthHelper.getSecurePassword(data.getPassword(), user.getPasswordSalt());
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

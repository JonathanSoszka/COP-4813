package Controllers;

import DTO.RegistrationForm;
import Entities.User;
import Helpers.HibernateHelper;
import Helpers.HttpGet;
import Helpers.HttpPost;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import shared.ButtonMethod;

public class RegistrationControllerHelper
        extends ControllerHelperBase {

    @HttpGet(isDefault = true)
    public void getRegisterView() throws ServletException, IOException {
        forwardToJsp("register.jsp");
    }

    @HttpPost(method = "register")
    public void registerMethod() throws IOException, ServletException {
        request.setAttribute("helper", this);
        RegistrationForm data = new RegistrationForm();
        fillObjectFromRequest(data);
        if (!isValid(data)) {
            forwardToJsp("register.jsp");
            return;
        }

        User newUser = new User();
        newUser.setUsername(data.getUsername());

        try {
            newUser.updatePassword(data.getPassword());
        } catch (NoSuchAlgorithmException ex) {
            //swallow
        }
        HibernateHelper.updateDB(newUser);
        redirectToController("login");
    }
}

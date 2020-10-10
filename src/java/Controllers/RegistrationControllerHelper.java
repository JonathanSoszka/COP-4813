/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DTO.RegistrationForm;
import Entities.User;
import Helpers.HibernateHelper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import shared.ButtonMethod;

public class RegistrationControllerHelper
        extends ControllerHelperBase {

    RegistrationForm data;

    public RegistrationForm getData() {
        return data;
    }

    @Override
    public void doGet() throws ServletException, IOException {
        forwardToJsp("register.jsp");
    }

    @Override
    public void doPost() throws ServletException, IOException {
        executeButtonMethod();
    }

    @ButtonMethod(buttonName = "register", isDefault = true)
    public void registerMethod() throws IOException, ServletException {
        request.setAttribute("helper", this);
        data = RegistrationForm.buildFromRequest(request);
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

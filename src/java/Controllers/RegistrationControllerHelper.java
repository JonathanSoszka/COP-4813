/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import Helpers.HibernateHelper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import shared.ButtonMethod;

/**
 *
 * @author Jonathan
 */
public class RegistrationControllerHelper
        extends ControllerHelperBase {

    @Override
    public void doGet() throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    public void doPost() throws ServletException, IOException {
        executeButtonMethod();
    }

    @ButtonMethod(buttonName = "register", isDefault = true)
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
        redirectToController("login");
    }

}

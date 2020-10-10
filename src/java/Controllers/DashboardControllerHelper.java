 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import com.cedarsoftware.util.io.JsonReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DashboardControllerHelper extends ControllerHelperBase {

    @Override
    public void doGet() throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        User user = (User) getCookieAsObject("user");
        if (user != null) {
            request.setAttribute("user", user);
            forwardToJsp("dashboard.jsp");
        } else {
            redirectToController("login");
        }

    }

}

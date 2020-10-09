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

/**
 *
 * @author Jonathan
 */
public class DashboardControllerHelper extends ControllerHelperBase {

    public DashboardControllerHelper(HttpServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response) {
        super(servlet, request, response);
    }

    @Override
    public void doGet() throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie userCookie;
        User user = null;
        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("user"))
                {
                    user = (User)JsonReader.jsonToJava(cookie.getValue());
                    request.setAttribute("user", user);
                    
                }
            }
        }
        if(user != null)
        {
            request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
        }
        else{
            response.sendRedirect("/DndBuddy/login/login.jsp");
        }
        
    }

}

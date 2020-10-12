package Controllers;

import Entities.User;
import Helpers.HttpGet;
import com.cedarsoftware.util.io.JsonReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DashboardControllerHelper extends ControllerHelperBase {

    @HttpGet(isDefault = true)
    public void getDashboardView() throws ServletException, IOException {
        forwardToJsp("dashboard.jsp");
    }
}

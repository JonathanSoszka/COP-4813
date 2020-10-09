

package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DashboardController extends HttpServlet {
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        DashboardControllerHelper helper = new DashboardControllerHelper(this, request, response);
        helper.doGet();
    }
    

}

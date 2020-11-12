package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 This is the base controller, all controller should extend this with  a constructor like so

 public DashboardController() {
 super(new DashboardControllerHelper());
 }

 extending this class means we don't need to defin doGet and doPost for every controller.
 */
public class ControllerBase<T extends ControllerHelperBase> extends HttpServlet {

    private T _helper;
    
    //If this is set to true the controller will be protected(the user must be logged in)
    private boolean _secure;

    //constructor to set security value
    public ControllerBase(T helper, boolean secure) {
        _helper = helper;
        _secure = secure;
    }

    //constructor to default secure to true
    public ControllerBase(T helper) {
        _helper = helper;
        _secure = true;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        _helper.InitHelperBase(this, request, response);
        if (isAllowed()) {
            _helper.doGet();
        }
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        _helper.InitHelperBase(this, request, response);
        if (isAllowed()) {
            _helper.doPost();
        }
    }

    //helper to decide if the user is allowed to access the controller
    private boolean isAllowed() throws IOException {
        if (_secure == false) {
            return true;
        } else {
            return _helper.verifyAuth();
        }
    }
}

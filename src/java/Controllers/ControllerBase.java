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

    public ControllerBase(T helper) {
        _helper = helper;
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        _helper.InitHelperBase(this, request, response);
        _helper.doGet();

    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        _helper.InitHelperBase(this, request, response);
        _helper.doPost();

    }
}

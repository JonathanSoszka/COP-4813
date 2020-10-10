package Controllers;

import Entities.User;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.log4j.Logger;
import org.hibernate.validator.engine.PathImpl;
import shared.ButtonMethod;
import static shared.HelperBaseCh4.writeError;

public class ControllerHelperBase {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpServlet servlet;
    protected Logger logger;
    protected static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    protected static final Validator validator = validatorFactory.getValidator();
    java.util.Map<String, String> errorMap = new java.util.HashMap<String, String>();
    private Method methodDefault;

    public void InitHelperBase(HttpServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response) {
        this.servlet = servlet;
        this.request = request;
        this.response = response;
    }

    /*HTTP Methods*/
    public void doGet()
            throws ServletException, IOException {
        response.getWriter().print("The doGet method must be overridden"
                + " in the class that extends HelperBase.");
    }

    public void doPost()
            throws ServletException, IOException {
        response.getWriter().print("The doPost method must be overridden"
                + " in the class that extends HelperBase.");
    }

    /*Request Helper Methods*/
    
    //returns absolute path to the provided JSP
    protected String jspLocation(String page) {
        return "/WEB-INF/" + page;
    }

    //returns absolute path to the provided controller
    protected String controllerLocation(String controller) {
        return "/DndBuddy/" + controller;
    }

    //navigate to a controller with a new GET request
    protected void redirectToController(String controller) throws IOException {
        response.sendRedirect(controllerLocation(controller));
    }

    //forwards the current request to a controller
    protected void forwardToController(String controller) throws ServletException, IOException {
        request.getRequestDispatcher(controllerLocation(controller)).forward(request, response);
    }

    //forwards the current request to a JSP
    protected void forwardToJsp(String jsp) throws ServletException, IOException {
        request.getRequestDispatcher(jspLocation(jsp)).forward(request, response);
    }

    /*Validator Methods*/
    public void setErrors(Object data) {

        Set<ConstraintViolation<Object>> violations = validator.validate(data);

        errorMap.clear();
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> msg : violations) {
                PathImpl value = (PathImpl) msg.getPropertyPath();
                errorMap.put((String) value.getLeafNode().getName(),
                        msg.getMessage());
            }
        }
    }

    public void clearErrors() {
        if (errorMap != null) {
            errorMap.clear();
        }
    }

    public boolean isValid(Object data) {
        setErrors(data);
        return errorMap.isEmpty();
    }

    public java.util.Map getErrors() {
        return errorMap;
    }

    public boolean isValidProperty(String name) {
        String msg = errorMap.get(name);
        return msg == null || msg.equals("");
    }

    /*Cookie Methods*/
    protected Cookie getCookie(String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    protected String getCookieValue(String cookieName) {
        Cookie cookie = getCookie(cookieName);
        if (cookie == null) {
            return null;
        }

        return cookie.getValue();
    }

    protected Object getCookieAsObject(String cookieName) throws IOException {
        String cookieString = getCookieValue(cookieName);
        if (cookieString == null) {
            return null;
        }
        return JsonReader.jsonToJava(cookieString);
    }

    protected Cookie objectToCookie(Object obj, String name, String path) throws IOException {
        String jsonStr = JsonWriter.objectToJson(obj);
        Cookie cookie = new Cookie(name, jsonStr);
        cookie.setPath(path);
        return cookie;

    }

    /*ButtonMethod*/
    protected void executeButtonMethod()
            throws ServletException, IOException {
        methodDefault = null;
        Class clazz = this.getClass();
        Class enclosingClass = clazz.getEnclosingClass();
        while (enclosingClass != null) {
            clazz = this.getClass();
            enclosingClass = clazz.getEnclosingClass();
        }
        try {
            executeButtonMethod(clazz, true);
        } catch (Exception ex) {
            writeError(request, response,
                    "Button Method Error", ex);
        }
    }

    protected void executeButtonMethod(Class clazz,
            boolean searchForDefault)
            throws IllegalAccessException, InvocationTargetException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            ButtonMethod annotation
                    = method.getAnnotation(ButtonMethod.class);
            if (annotation != null) {
                if (searchForDefault && annotation.isDefault()) {
                    methodDefault = method;
                }
                if (request.getParameter(annotation.buttonName())
                        != null) {
                    invokeButtonMethod(method);
                    return;
                }
            }
        }

        if (methodDefault != null) {
            invokeButtonMethod(methodDefault);
        }
    }

    protected void invokeButtonMethod(Method buttonMethod)
            throws IllegalAccessException, InvocationTargetException {

        try {
            buttonMethod.invoke(this,
                    (Object[]) null);
        } catch (IllegalAccessException iae) {
            logger.error("(invoke) Button method is not public.",
                    iae);
            throw iae;
        } catch (InvocationTargetException ite) {
            logger.error("(invoke) Button method exception",
                    ite);
            throw ite;
        }
    }
}

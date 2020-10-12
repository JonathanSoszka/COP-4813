package Controllers;

import DTO.UserDTO;
import Entities.User;
import Helpers.HibernateHelper;
import Helpers.HttpGet;
import Helpers.HttpPost;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.beanutils.BeanUtils;
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
        httpGet();
    }

    public void doPost()
            throws ServletException, IOException {
        request.setAttribute("helper", this);
        httpPost();
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

    //forwards the current request to another controller
    protected void forwardToController(String controller) throws ServletException, IOException {
        request.getRequestDispatcher(controllerLocation(controller)).forward(request, response);
    }

    //forwards the current request to a JSP
    protected void forwardToJsp(String jsp) throws ServletException, IOException {
        request.getRequestDispatcher(jspLocation(jsp)).forward(request, response);
    }

    protected void fillObjectFromRequest(Object obj) throws IOException {
        try {
            BeanUtils.populate(obj, request.getParameterMap());
        } catch (Exception ex) {
            response.getWriter().print(ex.getMessage());

        }
    }

    //Used to verify that a user is logged in.
    protected boolean verifyAuth() throws IOException {
        //if user cookie is present save it to session, otherwise give the stinky boot
        UserDTO userDTO = null;
        if (getCookieAsObject("user") != null) {
            userDTO = (UserDTO) getCookieAsObject("user");
        }
        if (userDTO == null) {
            redirectToController("login");
            return false;
        } else if (!userDTO.isValidSecurityKey()) {
            redirectToController("login");
            return false;
        } else {
            User user = UserDTO.mapToEntity(userDTO);
            User userFromSession = (User) getFromSession("user");
            if (userFromSession == null) {
                user = (User) HibernateHelper.getFirstMatch(user, "id", user.getId());
                addToSession("user", user);
            }
            return true;
        }
    }


    /*Session Storage Methods*/
    protected void addToSession(String name, Object obj) {
        request.getSession().setAttribute(name, obj);
    }

    protected Object getFromSession(String name) {
        return request.getSession().getAttribute(name);
    }

    protected void removeFromSession(String name) {
        request.getSession().removeAttribute(name);
    }

    protected void updateUserSession() {
        User user = (User) getFromSession("user");
        user = (User) HibernateHelper.getFirstMatch(user, "username", user.getUsername());
        addToSession("user", user);
    }

    //Methods to work with @HttpPost and @HttpGet annotations
    protected void httpGet() throws IOException, ServletException {
        Method defaultMethod = null;
        Class clazz = this.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            HttpGet annotation = method.getAnnotation(HttpGet.class
            );
            if (annotation
                    != null) {
                if (annotation.isDefault()) {
                    defaultMethod = method;
                }
                if (annotation.url().equals(getTrimmedUri())) {
                    invokeMethod(method);
                    return;
                }
            }
        }
        if (defaultMethod != null) {
            invokeMethod(defaultMethod);
        } else {
            response.getWriter().print("HTTP Get could not be resolved, "
                    + "make sure you have a method mapped with "
                    + "an @HttpGet annotation for this route "
                    + request.getRequestURI());
        }
    }

    protected void httpPost() throws IOException, ServletException {
        Method defaultMethod = null;
        Class clazz = this.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            HttpPost annotation = method.getAnnotation(HttpPost.class
            );
            if (annotation
                    != null) {
                if (annotation.isDefault()) {
                    defaultMethod = method;
                }
                if (annotation.method().equals(request.getParameter("method"))) {
                    invokeMethod(method);
                    return;
                }
            }
        }
        if (defaultMethod != null) {
            invokeMethod(defaultMethod);
        } else {
            response.getWriter().print("HTTP Post could not be resolved, "
                    + "make sure you have a method mapped with "
                    + "an @HttpPost annotation for this route "
                    + request.getRequestURI());
        }
    }

    private void invokeMethod(Method method) throws IOException {
        try {
            method.invoke(this, (Object[]) null);
        } catch (InvocationTargetException ex) {
            response.getWriter().write(ex.getTargetException().getMessage());

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ControllerHelperBase.class
                    .getName()).log(Level.SEVERE, null, ex);
            response.getWriter()
                    .print(ex.getMessage());
        }
    }

    String getTrimmedUri() {
        String uri = request.getRequestURI();
        uri = uri.replace("/DndBuddy/", "");
        uri = uri.replace("/DndBuddy", "");
        return uri;
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
    //fetch the cookie object
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

    //get the value of a cookie
    protected String getCookieValue(String cookieName) {
        Cookie cookie = getCookie(cookieName);
        if (cookie == null) {
            return null;
        }

        return cookie.getValue();
    }

    //deserialize json cookies into java objects
    protected Object getCookieAsObject(String cookieName) {
        String cookieString = getCookieValue(cookieName);

        if (cookieString == null) {
            return null;
        }
        try {
            return JsonReader.jsonToJava(cookieString);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ControllerHelperBase.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Set cookie to string value
    protected void setCookie(String cookieName, String value) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    //set cookie to JSON representation of an object
    protected void setCookie(String cookieName, Object obj) throws IOException {
        String jsonStr = JsonWriter.objectToJson(obj);
        Cookie cookie = new Cookie(cookieName, jsonStr);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    //removes a cookie
    protected void removeCookie(String name) {
        Cookie cookie = getCookie(name);
        cookie.setValue(null);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    /*ButtonMethod from class*/
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
                    = method.getAnnotation(ButtonMethod.class
                    );
            if (annotation
                    != null) {
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

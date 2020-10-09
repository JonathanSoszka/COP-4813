package Controllers;

import Entities.User;
import com.cedarsoftware.util.io.JsonReader;
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

public class ControllerHelperBase {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpServlet servlet;
    protected Logger logger;
    protected static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    protected static final Validator validator = validatorFactory.getValidator();
    java.util.Map<String, String> errorMap = new java.util.HashMap<String, String>();

    public void InitHelperBase(HttpServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response) {
        this.servlet = servlet;
        this.request = request;
        this.response = response;
    }

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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {

    private String Username;
    private String Password;

    public static LoginForm buildFromRequest(HttpServletRequest request) {
        LoginForm loginForm = new LoginForm();
        loginForm.Username = request.getParameter("username");
        loginForm.Password = request.getParameter("password");
        return loginForm;
    }

    @NotEmpty(message="Username Required")
    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    @NotEmpty(message="Password Required")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

}

package DTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class RegistrationForm {

    private String Username;
    private String Password;
    private String ConfirmPassword;

    public RegistrationForm() {

    }

    public static RegistrationForm buildFromRequest(HttpServletRequest request) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.Username = request.getParameter("username");
        registrationForm.Password = request.getParameter("password");
        registrationForm.ConfirmPassword = request.getParameter("confirmPassword");
        return registrationForm;
    }

    @NotEmpty(message = "Username is required")
    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    @NotEmpty(message = "Password is required")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @NotEmpty(message = "Please confirm your password")
    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.ConfirmPassword = confirmPassword;
    }

    @AssertTrue(message = "Confirm password does not match")
    public boolean isConfirmPasswordMatch() {
        return ConfirmPassword.equals(Password);
    }
}

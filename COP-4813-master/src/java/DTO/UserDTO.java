/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import Entities.User;
import Helpers.AuthHelper;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan
 */
public class UserDTO {

    private long id;
    private String Username;
    private String SecurityKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public static UserDTO mapFromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setSecurityKey();
        return dto;
    }

    public static User mapToEntity(UserDTO dto) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setId(dto.getId());
        return user;
    }

    public String getSecurityKey() {

        return SecurityKey;
    }

    public void setSecurityKey() {
        byte[] salt = "".getBytes();
        String uniqueId = Username + String.valueOf(id);
        SecurityKey = AuthHelper.getSecurePassword(uniqueId, salt);
    }

    public boolean isValidSecurityKey() {
        String providedKey = SecurityKey;
        setSecurityKey();
        return providedKey.equals(SecurityKey);
    }

}

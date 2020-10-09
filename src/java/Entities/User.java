package Entities;

import Helpers.AuthHelper;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class User extends PersistentBase implements Serializable{

    private String Username;
    private String PasswordHash;
    private byte[] PasswordSalt;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String PasswordHash) {
        this.PasswordHash = PasswordHash;
    }

    public byte[] getPasswordSalt() {
        return PasswordSalt;
    }

    public void setPasswordSalt(byte[] PasswordSalt) {
        this.PasswordSalt = PasswordSalt;
    }

    public void updatePassword(String password) throws NoSuchAlgorithmException {
        byte[] salt = AuthHelper.getSalt();
        String passwordHash = AuthHelper.getSecurePassword(password, salt);
        PasswordSalt = salt;
        PasswordHash = passwordHash;
    }

}

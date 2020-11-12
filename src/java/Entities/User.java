package Entities;

import Helpers.AuthHelper;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class User extends PersistentBase implements Serializable {

    private String Username;
    private String PasswordHash;
    private byte[] PasswordSalt;

    private List<UserCharacter> userCharacters;
    private List<UserNote> userNotes;

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

    @OneToMany(targetEntity = UserCharacter.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    public List<UserCharacter> getCharacters() {
        return userCharacters;
    }

    public void setCharacters(List<UserCharacter> Characters) {
        this.userCharacters = Characters;
    }

}

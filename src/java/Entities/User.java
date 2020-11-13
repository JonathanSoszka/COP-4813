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
import org.hibernate.annotations.*;

@Entity
public class User extends PersistentBase implements Serializable {

    private String Username;
    private String PasswordHash;
    private byte[] PasswordSalt;

    private List<UserCharacter> userCharacters;
    private List<Campaign> campaigns;
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

    @OneToMany(targetEntity = UserCharacter.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "USER_ID")
    public List<UserCharacter> getCharacters() {
        return userCharacters;
    }

    public void setCharacters(List<UserCharacter> Characters) {
        this.userCharacters = Characters;
    }

    @OneToMany(targetEntity = Campaign.class)
    @JoinColumn(name = "USER_ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Campaign> Campaigns) {
        this.campaigns = Campaigns;
    }
    @OneToMany(targetEntity = UserNote.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "USER_ID")
    public List<UserNote> getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(List<UserNote> userNotes) {
        this.userNotes = userNotes;
    }

}

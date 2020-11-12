/*
    AUTHOR: Wyatt O'Steen
*/

package Entities;

import java.io.Serializable;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class UserNote extends PersistentBase implements Serializable {
    
    private String Text;
    private String Vis;
    private String Author;
    
    private User user;
    
    /*
    private Campaign campaign;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CAMPAIGN_ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Campaign getCampaign() {
        return campaign;
    }
    
    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }
    */

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    @NotNull
    public String getVis() {
        return Vis;
    }
    
    public void setVis(String Vis) {
        this.Vis = Vis;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NotNull
    public String getAuthor() {
        return Author;
    }
    public void setAuthor(String Author) {
        this.Author = Author;
    }
    
}

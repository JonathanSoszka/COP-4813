/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Jonathan
 */
@Entity
public class Campaign extends PersistentBase implements Serializable {

    private String Name;
    private User CreatedBy;
    private List<UserCharacter> Characters;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    //@LazyCollection(LazyCollectionOption.FALSE)
    public User getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(User CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    @OneToMany(targetEntity = UserCharacter.class)
    @JoinColumn(name = "CAMPAIGN_ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<UserCharacter> getCharacters() {
        return Characters;
    }

    public void setCharacters(List<UserCharacter> Characters) {
        this.Characters = Characters;
    }
}

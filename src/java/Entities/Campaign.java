/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
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

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATEDBY_ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    public User getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(User CreatedBy) {
        this.CreatedBy = CreatedBy;
    }
}

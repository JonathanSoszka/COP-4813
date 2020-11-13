package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class UserCharacter extends PersistentBase implements Serializable {

    private String Name;
    private String Race;
    private String CharacterClass;
    private String Alignment;
    private String Background;
    private int Strength;
    private int Dexterity;
    private int Constitution;
    private int Intelligence;
    private int Wisdom;
    private int Charisma;
    private int MaxHp;
    private int Hp;
    private int Level;
    private User user;
    private Campaign Campaign;

    public UserCharacter() {

    }

    public void rollRandomStats() {
        setStrength(statRoll());
        setDexterity(statRoll());
        setConstitution(statRoll());
        setIntelligence(statRoll());
        setWisdom(statRoll());
        setCharisma(statRoll());
    }

    private int statRoll() {
        //DND rules roll four D6 take the sum of the highest 3
        Random rand = new Random();
        int max = 6;
        int min = 1;
        int rolls[];

        int lowestRoll = 7;
        int rollSum = 0;
        for (int i = 0; i < 4; i++) {
            int roll = rand.nextInt((max - min) + 1) + min;
            rollSum += roll;
            if (roll < lowestRoll) {
                lowestRoll = roll;
            }
        }
        rollSum -= lowestRoll;
        return rollSum;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRace() {
        return Race;
    }

    public void setRace(String Race) {
        this.Race = Race;
    }

    public String getAlignment() {
        return Alignment;
    }

    public void setAlignment(String Alignment) {
        this.Alignment = Alignment;
    }

    public String getBackground() {
        return Background;
    }

    public void setBackground(String Background) {
        this.Background = Background;
    }

    public int getStrength() {
        return Strength;
    }

    public void setStrength(int Strength) {
        this.Strength = Strength;
    }

    public int getDexterity() {
        return Dexterity;
    }

    public void setDexterity(int Dexterity) {
        this.Dexterity = Dexterity;
    }

    public int getConstitution() {
        return Constitution;
    }

    public void setConstitution(int Constitution) {
        this.Constitution = Constitution;
    }

    public int getIntelligence() {
        return Intelligence;
    }

    public void setIntelligence(int Intelligence) {
        this.Intelligence = Intelligence;
    }

    public int getWisdom() {
        return Wisdom;
    }

    public void setWisdom(int Wisdom) {
        this.Wisdom = Wisdom;
    }

    public int getCharisma() {
        return Charisma;
    }

    public void setCharisma(int Charisma) {
        this.Charisma = Charisma;
    }

    public int getMaxHp() {
        return MaxHp;
    }

    public void setMaxHp(int MaxHp) {
        this.MaxHp = MaxHp;
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int Hp) {
        this.Hp = Hp;
    }

    public String getCharacterClass() {
        return CharacterClass;
    }

    public void setCharacterClass(String CharacterClass) {
        this.CharacterClass = CharacterClass;
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

    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CAMPAIGN_ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Campaign getCampaign() {
        return Campaign;
    }

    public void setCampaign(Campaign Campaign) {
        this.Campaign = Campaign;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Entities.User;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServlet;
import Entities.UserCharacter;

public class InitHibernate extends HttpServlet {

    public void init() {
        Class tables[] = {
            User.class,
            UserCharacter.class
        };
        boolean create = Boolean.parseBoolean(this.getInitParameter("create"));
        if (create) {
            HibernateHelper.createTable(tables);
        }
        HibernateHelper.initSessionFactory(tables);;
        if (create) {
            applySeedData();
        }
    }

    private void applySeedData() {
        seedUserData();
        seedCharacterData();
    }

    private void seedUserData() {
        try {
            User user = new User();
            user.setUsername("testuser");
            user.updatePassword("password");
            HibernateHelper.updateDB(user);
        } catch (NoSuchAlgorithmException ex) {
            //swallow
        }
    }

    private void seedCharacterData() {
        String classes[] = {"Fighter", "rouge", "Mage"};
        String races[] = {"Human", "Dwarf", "Half-Orc"};
        String alignments[] = {"Chaotic Good", "Chaotic Evil", "Lawful Good"};
        User userFromDb = (User) HibernateHelper.getFirstMatch(new User(), "username", "testuser");

        for (int i = 0; i < 3; i++) {
            UserCharacter character = new UserCharacter();
            character.setName("Character " + Integer.toString(i));
            character.setAlignment(alignments[i]);
            character.setCharacterClass(classes[i]);
            character.setRace(races[i]);
            character.rollRandomStats();
            character.setUser(userFromDb);
            HibernateHelper.updateDB(character);
        }
    }
}

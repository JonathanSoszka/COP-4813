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

//This code gets called at app startup to init hibernate
public class InitHibernate extends HttpServlet {

    public void init() {
        //list any entities for persistance here
        Class tables[] = {
            User.class,
            UserCharacter.class
        };

        //check web.xml for create param
        boolean create = Boolean.parseBoolean(this.getInitParameter("create"));
        if (create) {
            HibernateHelper.createTable(tables);
        }
        HibernateHelper.initSessionFactory(tables);;
        if (create) {
            applySeedData();
        }
    }

    //adds seed data to database
    private void applySeedData() {
        seedUserData();
        seedCharacterData();
    }

    //seed user data
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

    //seed character data
    private void seedCharacterData() {
        User userFromDb = (User) HibernateHelper.getFirstMatch(new User(), "username", "testuser");

        for (int i = 0; i < 5; i++) {
            UserCharacter character = new UserCharacter();
            character.setName("Character " + Integer.toString(i + 1));
            character.setAlignment(Constants.alignments[i % 9]);
            character.setCharacterClass(Constants.classes[i % 12]);
            character.setRace(Constants.races[i % 9]);
            character.setBackground(Constants.backgrounds[i % 3]);
            character.rollRandomStats();
            character.setUser(userFromDb);
            character.setLevel(i + 1);
            HibernateHelper.updateDB(character);
        }
    }
}

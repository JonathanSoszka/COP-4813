/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Entities.*;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import javax.servlet.http.HttpServlet;

//This code gets called at app startup to init hibernate
public class InitHibernate extends HttpServlet {

    public void init() {
        //list any entities for persistance here
        Class tables[] = {
            User.class,
            Campaign.class,
            UserCharacter.class,
            UserNote.class,};

        //check web.xml for create param
        boolean create = Boolean.parseBoolean(this.getInitParameter("create"));
        if (create) {
            //create db
            /*try {
             Class.forName("com.mysql.jdbc.Driver");
             DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "password")
             .createStatement().executeUpdate("CREATE DATABASE dndbuddy");
             } catch (Exception e) {
             e.printStackTrace();
             }*/
            //create tables
            try
            {
                HibernateHelper.createTable(tables);
            }
            catch (Exception ex)
            {
                Exception exx = ex;
            }
        }
        HibernateHelper.initSessionFactory(tables);;
        if (create) {
            applySeedData();
        }
    }

    //adds seed data to database
    private void applySeedData() {
        seedUserData();
        seedCampaignData();
        seedCharacterData();
        seedNoteData();
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

    //seed campaign data
    private void seedCampaignData() {
        User userFromDb = (User) HibernateHelper.getFirstMatch(new User(), "username", "testuser");
   
        Campaign campaign = new Campaign();
        campaign.setCreatedBy(userFromDb);
        campaign.setName("Sample Campagin");
        HibernateHelper.updateDB(campaign);
    }

    //seed character data
    private void seedCharacterData() {
        User userFromDb = (User) HibernateHelper.getFirstMatch(new User(), "username", "testuser");
        Campaign campaignFromDb = (Campaign) HibernateHelper.getKeyData(Campaign.class, 1);

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
            character.setCampaign(campaignFromDb);
            HibernateHelper.updateDB(character);
        }
    }

    private void seedNoteData() {
        User userFromDb = (User) HibernateHelper.getFirstMatch(new User(), "username", "testuser");

        for (int i = 1; i < 5; i++) {
            UserNote note = new UserNote();
            note.setText("note number " + Integer.toString(i));
            note.setUser(userFromDb);
            note.setAuthor(userFromDb.getUsername());
            note.setVis("private");
            HibernateHelper.updateDB(note);
        }
        for (int i = 1; i < 5; i++) {
            UserNote note = new UserNote();
            note.setText("note number " + Integer.toString(i+5));
            note.setUser(userFromDb);
            note.setAuthor(userFromDb.getUsername());
            note.setVis("public");
            HibernateHelper.updateDB(note);
        }
    }
}

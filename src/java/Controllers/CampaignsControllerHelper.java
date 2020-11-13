/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Campaign;
import Entities.User;
import Entities.UserCharacter;
import Helpers.HibernateHelper;
import Helpers.HttpGet;
import Helpers.HttpPost;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;

/**
 *
 * @author Jonathan
 */
public class CampaignsControllerHelper extends ControllerHelperBase {

    @HttpGet(url = "campaigns", isDefault = true)
    public void GetCampaignsView() throws ServletException, IOException {
        User user = (User) getFromSession("user");
        request.setAttribute("campaigns", user.getCampaigns());

        forwardToJsp("campaigns/campaignsList.jsp");
    }

    @HttpGet(url = "campaigns/create")
    public void GetCampaignCreateView() throws ServletException, IOException {
        forwardToJsp("campaigns/createCampaign.jsp");
    }

    @HttpGet(url = "campaigns/detail")
    public void GetCampaignDetailView() throws ServletException, IOException {
        long campaignId = Integer.valueOf(request.getParameter("id"));
        Campaign campaign = (Campaign) HibernateHelper.getKeyData(Campaign.class, campaignId);
        request.setAttribute("campaign", campaign);
        addToSession("campaign", campaign);
        forwardToJsp("campaigns/campaignDetail.jsp");
    }

    @HttpGet(url = "campaigns/addCharacter")
    public void GetCharacterAddView() throws ServletException, IOException {
        request.setAttribute("campaign", getFromSession("campaign"));
        forwardToJsp("campaigns/addCharacter.jsp");
    }

    @HttpPost(method = "createCampaign")
    public void CreateCampaign() throws IOException {
        Campaign campaign = new Campaign();
        fillObjectFromRequest(campaign);

        User user = (User) getFromSession("user");
        campaign.setCreatedBy(user);
        HibernateHelper.updateDB(campaign);

        updateUserSession();
        redirectToController("campaigns");
    }

    @HttpPost(method = "addCharacter")
    public void AddCharacter() throws IOException, ServletException {
        String username = (String) request.getParameter("username");
        String characterName = (String) request.getParameter("characterName");

        User user = (User) HibernateHelper.getFirstMatch(new User(), "username", username);
        if (user == null) {
            request.setAttribute("error", "User does not exist");
            forwardToJsp("campaigns/addCharacter.jsp");
        }

        UserCharacter characterToAdd = new UserCharacter();
        for (UserCharacter character : user.getCharacters()) {
            if (character.getName().equals(characterName)) {
                characterToAdd = character;
                break;
            }
        }

        if (characterToAdd == null) {
            request.setAttribute("error", "Character does not exist");
            forwardToJsp("campaigns/addCharacter.jsp");
        }
        
        Campaign campaign = (Campaign) getFromSession("campaign");
        characterToAdd.setCampaign(campaign);
        HibernateHelper.updateDB(characterToAdd);
        
        updateUserSession();
        
        redirectToController("campaigns/detail?id=" + campaign.getId());

    }
    
    @HttpPost(method = "removeCharacter")
    public void RemoveCharacter() throws IOException{
        Campaign campaign = (Campaign) getFromSession("campaign");
        long characterId = Integer.valueOf(request.getParameter("characterId"));
        
        UserCharacter character = (UserCharacter) HibernateHelper.getKeyData(UserCharacter.class, characterId);
        character.setCampaign(null);
        HibernateHelper.updateDB(character);
        updateUserSession();
        redirectToController("campaigns/detail?id=" + campaign.getId());
    }

    @HttpPost(method = "deleteCampaign")
    public void DeleteCampaign() throws IOException {
        Campaign campaign = new Campaign();
        fillObjectFromRequest(campaign);
        HibernateHelper.removeDB(campaign);

        updateUserSession();
        redirectToController("campaigns");
    }

}

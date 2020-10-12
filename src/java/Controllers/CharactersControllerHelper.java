package Controllers;

import Entities.User;
import Entities.UserCharacter;
import Helpers.Constants;
import Helpers.HibernateHelper;
import Helpers.HttpGet;
import Helpers.HttpPost;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import org.apache.commons.beanutils.BeanUtils;

public class CharactersControllerHelper extends ControllerHelperBase {

    @HttpGet(url = "characters", isDefault = true)
    public void getCharacterListView() throws ServletException, IOException {
        User user = new User();
        user = (User) request.getSession().getAttribute("user");
        user = (User) HibernateHelper.getFirstMatch(new User(), "username", user.getUsername());
        request.getSession().setAttribute("user", user);
        request.setAttribute("characters", user.getCharacters());

        forwardToJsp("characters/characterList.jsp");
    }

    @HttpGet(url = "characters/detail")
    public void getCharacterDetailView() throws ServletException, IOException {
        long characterId = Integer.valueOf(request.getParameter("id"));
        
        if (characterId == 0) {
            redirectToController("characters");
            return;
        }

        UserCharacter character = new UserCharacter();
        character = (UserCharacter) HibernateHelper.getFirstMatch(character, "id", characterId);
        if (character == null) {
            redirectToController("characters");
            return;
        }

        request.setAttribute("character", character);
        forwardToJsp("characters/characterDetail.jsp");
    }

    @HttpGet(url = "characters/create")
    public void getCharacterCreateView() throws ServletException, IOException {
        request.setAttribute("races", Constants.races);
        request.setAttribute("classes", Constants.classes);
        request.setAttribute("alignments", Constants.alignments);
        request.setAttribute("backgrounds", Constants.backgrounds);
        forwardToJsp("characters/characterCreate.jsp");
    }

    @HttpPost(method = "deleteCharacter")
    public void deleteCharacter() throws ServletException, IOException {
        long characterId = Integer.valueOf(request.getParameter("id"));
        UserCharacter character = new UserCharacter();
        character = (UserCharacter) HibernateHelper.getFirstMatch(character, "id", characterId);
        HibernateHelper.removeDB(character);
        redirectToController("characters");
    }

    @HttpPost(method = "createCharacter")
    public void createCharacter() throws IOException, InvocationTargetException, IllegalAccessException {
        UserCharacter character = new UserCharacter();
        fillObjectFromRequest(character);
        character.rollRandomStats();
        
        User user = new User();
        user = (User) request.getSession().getAttribute("user");
        character.setUser(user);
        
        HibernateHelper.updateDB(character);
        
        redirectToController("characters");
    }

}

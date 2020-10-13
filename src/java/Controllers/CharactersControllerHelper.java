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
        User user = (User) getFromSession("user");
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

    @HttpGet(url = "characters/edit")
    public void getCharacterEditView() throws IOException, ServletException {
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
        User user = (User) getFromSession("user");

        if (!character.getUser().getId().equals(user.getId())) {
            redirectToController("characters");
            return;
        }

        request.setAttribute("character", character);
        request.setAttribute("races", Constants.races);
        request.setAttribute("classes", Constants.classes);
        request.setAttribute("alignments", Constants.alignments);
        request.setAttribute("backgrounds", Constants.backgrounds);
        forwardToJsp("characters/characterEdit.jsp");
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
        updateUserSession();
        redirectToController("characters");
    }

    @HttpPost(method = "createCharacter")
    public void createCharacter() throws IOException, InvocationTargetException, IllegalAccessException {
        UserCharacter character = new UserCharacter();
        fillObjectFromRequest(character);
        character.rollRandomStats();
        character.setLevel(1);

        User user = (User) getFromSession("user");
        character.setUser(user);
        HibernateHelper.updateDB(character);

        updateUserSession();

        redirectToController("characters");
    }

    @HttpPost(method = "saveCharacter")
    public void saveCharacter() throws IOException, ServletException {
        UserCharacter character = new UserCharacter();
        fillObjectFromRequest(character);
        character.setUser(getUserFromSession());
        HibernateHelper.updateDB(character);
        request.setAttribute("character", character);
        updateUserSession();
        redirectToController("characters/detail?id=" + String.valueOf(character.getId()));
    }

}

# DND Buddy - COP 4813 Group Project

### [Jira](https://jonathansoszka.atlassian.net/jira/software/projects/DB/boards/6)

## Summarry
This application will serve as a companion tool for people playing Dungeons and Dragons.

## Team Members
* Jonathan Soszka
* Wyatt O'Steen
* Wynne Nicholas

## Contributing
Please create a new branch off of master whenever making changes, once you are done create a Pull Request for your code to be merged into master and another team member will review and approve.

## Project Structure

### Entities
This is where persisted classes live, if it will be stored in the DB put it here.

### DTOs
Objects used to transfer data between JSPs and controllers such as forms. making a DTO allows us to use the validator to validate the forms status.

### Helpers
Objects that provide some miscellaneous utility to the application.

### JSPs
Web pages that make up the UI of our application stored in `web-inf/`

## Controllers / ControllerHelpers
The meat of our application, requests get sent to a controller, from there the controller does any required processing to handle the request. JSP are accessed by a controller forwarding the request to them.

We have 2 robust base classes `ControllerBase` and `ControllerHelperBase` these helpers provide a lot of utility for managing requests. if we use these bases we actually do not need to write `doGet()` or `doPost()` methods at all

### Controllers
if we extend `ControllerBase` this is the only code needed in any controller
```
package Controllers;
public class CharactersController extends ControllerBase {

    public CharactersController() {
        super(new CharactersControllerHelper());
    }
}
```
This is because `doPost()` and `doGet()` are inherited from `ControllerBase`

### Controller Helpers
If extended from `ControllerHelperBase` we also don't need `doPost()` and `doGet()` here. our helper will take advantage of some custom annotations
`@HttpGet()` and `@HttpPost()` to know which code needs to be run for a given request.

#### Annotation Examples

* `@HttpGet(url="characters")` a method with this annotation will automatically run when a get request is sent to `DndBuddy/characters`

* `@HttpPost(method="deleteCharacter")` a method with this annotation will run if a post request is sent with the attribut `method="deleteCharacter"` set this attribute of the button submiting the post request like below

```
<form>
...
<button type="submit" name="method" value="deleteCharacter">Text</button>
</form>
```


Here is a full example using the `CharacterControllerHelper`
This contains 2 `@HttpGet` methods one for the character list page and the other for the character create page, as well as 2 `@HttpPost` methods, one to delete a character and the other to create one.
```
public class CharactersControllerHelper extends ControllerHelperBase {

    @HttpGet(url = "characters", isDefault = true)
    public void getCharacterListView() throws ServletException, IOException {
        User user = (User) getFromSession("user");
        request.setAttribute("characters", user.getCharacters());
        forwardToJsp("characters/characterList.jsp");
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

        User user = (User)getFromSession("user");
        character.setUser(user);
        HibernateHelper.updateDB(character);
        
        updateUserSession();

        redirectToController("characters");
    }
}
```
To allow a controller to be accessed from multiple urls simply give it two mappings

```
    <servlet-mapping>
        <servlet-name>CharactersController</servlet-name>
        <url-pattern>/characters</url-pattern>
        <url-pattern>/characters/create</url-pattern>
    </servlet-mapping>

```


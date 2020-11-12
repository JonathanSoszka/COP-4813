/*
    AUTHOR: Wyatt O'Steen
*/

package Controllers;

import Entities.User;
import Entities.UserCharacter;
import Entities.UserNote;
import Helpers.Constants;
import Helpers.HibernateHelper;
import Helpers.HttpGet;
import Helpers.HttpPost;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletException;
import org.apache.commons.beanutils.BeanUtils;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class UtilitiesControllerHelper extends ControllerHelperBase {

    /*
    @HttpGet(url = "utilities", isDefault = true)
    public void getUtilitiesListView() throws ServletException, IOException {
        forwardToJsp("utilities/utilitiesList.jsp");
    }
    */

    @HttpGet(url = "utilities/monster")
    public void getMonsterGenView() throws ServletException, IOException {
        
        forwardToJsp("utilities/monsterGen.jsp");
    }

    @HttpGet(url = "utilities/notes/edit")
    public void getNotesEditView() throws IOException, ServletException {
        User user = (User) getFromSession("user");
        List<UserNote> notes = (List<UserNote>) request.getSession().getAttribute("notes");
        if (notes == null) {
            notes = getNotesList(user.getId());
        }
        request.getSession().setAttribute("notes", notes);

        forwardToJsp("utilities/editNotes.jsp");
    }

    @HttpGet(url = "utilities/notes")
    public void getNotesView() throws ServletException, IOException {
        User user = (User) getFromSession("user");
        List<UserNote> notes = getNotesList(user.getId());
        request.getSession().setAttribute("notes", notes);
        
        forwardToJsp("utilities/notes.jsp");
    }

    @HttpPost(method = "deleteNote")
    public void deleteNote() throws ServletException, IOException {
        long noteId = Integer.valueOf(request.getParameter("deleteId"));
        UserNote note = new UserNote();
        note = (UserNote) HibernateHelper.getFirstMatch(note, "id", noteId);
        HibernateHelper.removeDB(note);
        List<UserNote> notes = (List<UserNote>) request.getSession().getAttribute("notes");
        for (UserNote tempNote : notes) {
            if (tempNote.getId() == noteId) { notes.remove(tempNote); break; }
        }
        updateUserSession();
        redirectToController("utilities/notes/edit");
    }

    @HttpPost(method = "addNote")
    public void addNote() throws IOException, InvocationTargetException, IllegalAccessException {
        List<UserNote> notes = new ArrayList<UserNote>();
        
        UserNote note = new UserNote();
        
        note.setVis("private");
        note.setText("");
        User user = (User) getFromSession("user");
        note.setAuthor(user.getUsername());
        note.setUser(user);
        HibernateHelper.updateDB(note);
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            ResultSet rs = DriverManager.getConnection("jdbc:mysql://localhost/dndbuddy", "root", "password").createStatement().executeQuery("SELECT id FROM usernote WHERE USER_ID = "+Long.toString(user.getId()));
            rs.beforeFirst();
            UserNote tempNote = null;
            while (rs.next()) {
                tempNote = new UserNote();
                Long id = rs.getLong("id");
                tempNote.setVis(request.getParameter("vis-"+Long.toString(id)));
                tempNote.setId(id);
                tempNote.setText(request.getParameter("text-"+Long.toString(id)));
                tempNote.setUser(user);
                tempNote.setAuthor(user.getUsername());
                notes.add(tempNote);
            }
            rs.close();
            notes.remove(tempNote);
        } catch (Exception e) { }
        
        notes.add(note);

        request.getSession().setAttribute("notes", notes);
        updateUserSession();

        redirectToController("utilities/notes/edit");
    }

    @HttpPost(method = "saveNotes")
    public void saveNotes() throws IOException, ServletException {
        User user = ((User)getFromSession("user"));
        List<UserNote> notes = new ArrayList<UserNote>();
        ResultSet rs = null;
        //fillObjectFromRequest(notes);
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            rs = DriverManager.getConnection("jdbc:mysql://localhost/dndbuddy", "root", "password").createStatement().executeQuery("SELECT id FROM usernote WHERE USER_ID = "+Long.toString(user.getId()));
            rs.beforeFirst();
            while (rs.next()) {
                UserNote note = new UserNote();
                Long id = rs.getLong("id");
                note.setVis(request.getParameter("vis-"+Long.toString(id)));
                note.setId(id);
                note.setText(request.getParameter("text-"+Long.toString(id)));
                note.setUser(user);
                note.setAuthor(user.getUsername());
                notes.add(note);
                HibernateHelper.updateDB(note);
            }
            rs.close();
        } catch (Exception e) { }

        request.getSession().setAttribute("notes", notes);
        updateUserSession();
        redirectToController("utilities/notes");
    }

}

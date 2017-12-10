package database;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import org.json.JSONObject;
import util.PropertiesReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User object that is used to store information about workspace and theme preferences in the database.
 *
 * @author Kelly Zhang
 * @author Simran Singh
 */
public class User extends TrackableObject {

    @Expose private String userName;
    @Expose private String themeName;
    @Expose private String workspaceName;
    @Expose private Map<String, Map<String, String>> properties;
    @Expose private ArrayList<String> games;
    @Expose private ArrayList<String> authoring;

    public User(String name) {
        //TODO: read and create these from the database... some issue about asynchronous and synchronous writing to database
        userName = name;
        themeName = PropertiesReader.value("defaults","theme");
        workspaceName = PropertiesReader.value("defaults", "workspace");
        games = new ArrayList<>();
        authoring = new ArrayList<>();
        createProperties();
    }

    private User() {}

    @Override
    public void initialize() {
        createProperties();
    }

    /**
     * @return the username associated with the profile
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets the user's theme to the current one in use
     * @param theme the new theme
     */
    public void setTheme(String theme) {
        themeName = theme;
    }

    public void setWorkspace(String workspace) {workspaceName = workspace;}

    public String getWorkspaceName() { return workspaceName; }

    /**
     * @return the string of the current theme that the user has active in the authoring environment
     */
    public String getThemeName() {
        return themeName;
    }

    public Map<String, Map<String, String>> getProperties() { return properties; }

    public ArrayList<String> getGames() { return games; }

    public void addGame(String game) { games.add(game); }

    public ArrayList<String> getAuthoring() { return authoring; }

    public void addAuthoring(String game) { authoring.add(game); }

    private void createProperties() {
        File folder = new File(PropertiesReader.value("defaults", "propertyPath"));
        File[] listOfFiles = folder.listFiles();
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.USER_SETTINGS);
        properties = new HashMap<>();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(PropertiesReader.value("defaults", "workspaceString"))) {
                String fileName = file.getName().substring(0, file.getName().length() - PropertiesReader.PROPERTIES_SUFFIX.length() - 1);
                properties.put(fileName, PropertiesReader.map(fileName));
                JSONObject jsonObject = JSONHelper.JSONForObject(properties.get(fileName));
                manager.writeJSONFile(fileName, jsonObject);
            }
        }
    }
}

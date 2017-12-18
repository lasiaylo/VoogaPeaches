package database;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import org.json.JSONObject;
import util.PropertiesReader;
import util.pubsub.PubSub;
import util.pubsub.messages.StringMessage;

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

    private static final String THEME_MESSAGE = "THEME_MESSAGE";

    @Expose private String userName;
    @Expose private String themeName;
    @Expose private String workspaceName;
    @Expose private Map<String, Map<String, String>> properties;
    @Expose private ArrayList<String> games;
    @Expose private ArrayList<String> authoring;

    /**
     * Creates a username from the given string and assigns defaults that are then changed automatically throughout
     * progression of the authoring
     *
     * @param name The username
     */
    public User(String name) {
        userName = name;
        themeName = PropertiesReader.value("defaults","theme");
        workspaceName = PropertiesReader.value("defaults", "workspace");
        games = new ArrayList<>();
        authoring = new ArrayList<>();
        createProperties();
    }

    private User() {}

    /**
     * On recreation of the user, this will allow the user to change their last theme name so that themes are stored
     * on the user.
     */
    @Override
    public void initialize() {
        PubSub.getInstance().subscribe(
                THEME_MESSAGE,
                (message) -> themeName = ((StringMessage)message).readMessage()
        );
        createProperties();
    }

    /**
     * @return the username associated with the profile
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param theme Sets the user's theme to the current one in use
     */
    public void setTheme(String theme) {
        themeName = theme;
    }

    /**
     * @param workspace The name of the last used workspace
     */
    public void setWorkspace(String workspace) {workspaceName = workspace;}

    /**
     * @return The name of the last workspace used
     */
    public String getWorkspaceName() { return workspaceName; }

    /**
     * @return the string of the current theme that the user has active in the authoring environment
     */
    public String getThemeName() {
        return themeName;
    }

    /**
     * @return All the properties associated with a user so that information about the users properties can be saved
     */
    public Map<String, Map<String, String>> getProperties() { return properties; }

    /**
     * @return Get games that a specific user can play.
     */
    public ArrayList<String> getGames() { return games; }

    /**
     * @return Get games that a specific user authors.
     */
    public ArrayList<String> getAuthoring() { return authoring; }

    /**
     * Creates the properties of a user by searching through the workspace property files and then recreating property
     * files as a map. The key is the name of the properties file and the value is another map that maps the name of the
     * property to the value
     */
    private void createProperties() {
        File folder = new File(PropertiesReader.value("defaults", "propertyPath"));
        File[] listOfFiles = folder.listFiles();
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.USER_SETTINGS);
        properties = new HashMap<>();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(
                    PropertiesReader.value("defaults", "workspaceString"))) {
                String fileName = file.getName().substring(
                        0, file.getName().length() - PropertiesReader.PROPERTIES_SUFFIX.length() - 1);
                properties.put(fileName, PropertiesReader.map(fileName));
                JSONObject jsonObject = JSONHelper.JSONForObject(properties.get(fileName));
                manager.writeJSONFile(fileName, jsonObject);
            }
        }
    }
}

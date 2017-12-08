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

    public User(String name) {
        userName = name;
        themeName = PropertiesReader.value("defaults","theme");
        workspaceName = PropertiesReader.value("defaults", "workspace");
        createProperties();
    }

    private User() {}

    @Override
    public void initialize() {
        createProperties();
    }

    public void setTheme(String theme) {themeName = theme;}

    public void setWorkspace(String workspace) {workspaceName = workspace;}

    public String getWorkspaceName() { return workspaceName; }

    public String getThemeName() { return themeName; }

    public Map<String, Map<String, String>> getProperties() { return properties; }

    public ArrayList<String> getGames() { return games; }

    public void setGames(ArrayList<String> games) { this.games = games; }

    public void addGame(String game) { games.add(game); }

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

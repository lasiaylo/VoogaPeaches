package database;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import util.PropertiesReader;

import java.io.File;
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

    public User(String name) {
        userName = name;
        themeName = PropertiesReader.value("defaults","theme");
        workspaceName = PropertiesReader.value("defaults", "workspace");
        createProperties();
    }

    public void setTheme(String theme) {themeName = theme;}

    public void setWorkspace(String workspace) {workspaceName = workspace;}

    private void createProperties() {
        File folder = new File(PropertiesReader.value("defaults", "propertyPath"));
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(PropertiesReader.value("defaults", "workspaceString"))) {
                String fileName = file.getName().substring(0, file.getName().length() - PropertiesReader.PROPERTIES_SUFFIX.length() - 1);
                properties.put(fileName, PropertiesReader.map(fileName));
            }
        }
    }

    public static void main(String[] args) {
        User user = new User("test");
    }
}

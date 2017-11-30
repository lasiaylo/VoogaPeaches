package database;

import database.firebase.TrackableObject;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import org.json.JSONObject;
import util.PropertiesReader;

import java.io.File;
import java.util.List;

/**
 * A class that provides an API for saving manipulating a game file within the database 
 */
public class GameSaver {

    /* Instance Variables */
    private String gameName;
    private JSONDataManager manager;
    private File gameFolder;

    /**
     * Creates a new GameSaver for saving the game to the database
     * @param gameName is a {@code String} representing the name to
     *                 save the game under in the database
     */
    public GameSaver(String gameName) {
        this.gameName = gameName;
        manager = new JSONDataManager(JSONDataManager.JSONDataFolders.GAMES);
        gameFolder = getGameFolder(gameName);
    }

    /**
     * Initializes a new folder for the game if one is not already present
     * within the data directory
     * @param gameName is a {@code String} that represents what the game will be named as
     * @return A {@code File} that represents the folder of the game within the
     * data/jsondata/games/ directorys
     */
    private File getGameFolder(String gameName) {
        if(!manager.folderExists(gameName)) manager.createFolder(gameName);
        return new File(PropertiesReader.path("game_folder") + gameName);
    }

    /**
     * Saves the list of trackable objects to the game. Updates all previously stored objects.
     * @param toSave is a {@code List<T extends TrackableObject>} that contains all the
     *               Trackable objects that you want to store
     * @param <T> is the class of the TrackableObject being stored
     */
    public <T extends TrackableObject> void saveTrackableObjects(List<T> toSave) {
        if (toSave.size() != 0) {
            String classFolder = gameName + "/" + toSave.get(0).getClass().getSimpleName();
            if(!manager.folderExists(classFolder)) manager.createFolder(classFolder);
            for (T object : toSave) {
                JSONObject jsonForm = JSONHelper.JSONForObject(object);
                String filepath = classFolder + "/" + jsonForm.get("UID");
                manager.writeJSONFile(filepath,jsonForm);
            }
        }
    }
}

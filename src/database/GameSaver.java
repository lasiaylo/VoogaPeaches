package database;

import com.google.firebase.database.FirebaseDatabase;
import database.firebase.FileStorageConnector;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import engine.entities.Entity;
import org.json.JSONObject;
import util.PropertiesReader;

import java.io.File;
import java.util.*;

/**
 * A class that provides an API for saving manipulating a game file within the database
 *
 * @author Walker Willetts
 */
public class GameSaver {

    /* Instance Variables */
    private String gameName;
    private JSONDataManager manager;
    private File gameFolder;
    private String UID;

    /**
     * Creates a new GameSaver for saving the game to the database
     * @param gameName is a {@code String} representing the name to
     *                 save the game under in the database
     */
    public GameSaver(String gameName) {
        this.gameName = gameName;
        manager = new JSONDataManager(JSONDataFolders.GAMES);
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
     */
    public void saveGame(Entity toSave) {
        saveRoot(toSave);
        String[] images = saveImageJSON(toSave);
        uploadImages(images);

    }

    /**
     * Uploads all the images specified by the array to Firebase so that, when the game
     * is pulled on other computers, it contains the proper image files
     * @param images is a {@code String[]} that contains the names of each local image file
     *               to upload
     */
    private void uploadImages(String[] images){
        FileStorageConnector connector = new FileStorageConnector("images");
        for(String image : images)
            try {
                connector.saveFile(new File("./data/filedata/images/" + image));
            } catch(Exception e){
                // Do Nothing if file wasn't saved
            }
    }

    /**
     * Saves the games root into a JSON file for later loading
     * @param toSave is a {@code Entity} representing the root
     *               of a game with all its components
     */
    private void saveRoot(Entity toSave) {
        // Convert root to JSON files
        JSONObject jsonForm = JSONHelper.JSONForObject(toSave);
        String filepath = gameName + "/root.json";
        manager.writeJSONFile(filepath, jsonForm);
        FirebaseDatabase.getInstance().getReference("gameNames").child(gameName).setValueAsync(toSave.UIDforObject());
        JSONObject save = new JSONObject(JSONHelper.JSONForObject(toSave).toString().replace("/", "|"));
        FirebaseDatabase.getInstance().getReference("games").child(toSave.UIDforObject()).setValueAsync(JSONHelper.mapFromJSON(save));
    }

    /**
     * Saves a JSON file containing the images needed for the game
     * to function appropriately
     * @param toSave is a {@code Entity} representing the root of the game
     * @return A {@String[]} that contains all the names of the images
     * used by the game
     */
    private String[] saveImageJSON(Entity toSave) {
        // Retrieve image names
        Set<String> imageNames = new HashSet<String>();
        retrieveImageNames(imageNames, toSave);
        String filepath = gameName + "/images.json";
        Map<String, String[]> imageMap = new HashMap<>();
        imageMap.put(toSave.UIDforObject(), imageNames.toArray(new String[0]));
        manager.writeJSONFile(filepath, new JSONObject(imageMap));
        List<String> names = new ArrayList<String>();
        imageNames.forEach(e -> names.add(e));
        FirebaseDatabase.getInstance().getReference("images").child(toSave.UIDforObject()).setValueAsync(names);
        return imageNames.toArray(new String[0]);
    }

    /**
     * Recursively traverses the Game root tree in order to collect the
     * names of all the images used by the game
     * @param currentSet is a {@code Set<String>} that contains the names of
     *                   all the Strings seen previously
     * @param root is a {@code Entity} representing the current root in the
     *             game that the traversal is at
     */
    private void retrieveImageNames(Set<String> currentSet, Entity root){
        if(root == null) return;
        String imageName = (String) ((HashMap<String,Object>)
                ((HashMap<String, Object>) root.getProperties().getOrDefault("scripts", new HashMap<>()))
                        .getOrDefault("imageScript",new HashMap<>())).getOrDefault("image_path","");
        if(!imageName.isEmpty() && !currentSet.contains(imageName)) currentSet.add(imageName);
        for(Entity child : root.getChildren())
            retrieveImageNames(currentSet, child);
    }
}

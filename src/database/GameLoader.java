package database;

import database.filehelpers.FileConverter;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import database.firebase.FileStorageConnector;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * The class that handles the actual loading in of a game for the
 * rest of the application to use.
 *
 * @author Walker Willetts
 */
public class GameLoader {

    /* Instance Variables */
    private Entity gameRoot;

    /**
     * Creates a new GameLoader for loading a game from the database
     * and goes ahead and loads in the game's root
     * @param gameName is a {@code String} representing the name to
     *                 load from the database
     */
    public GameLoader(String gameName) {
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.GAMES);
        JSONObject imageJSON = manager.readJSONFile(gameName + "/images.json");
        loadGameImages(imageJSON);
        JSONObject gameJSON = manager.readJSONFile(gameName + "/root.json");
        gameRoot = (new JSONToObjectConverter<Entity>(Entity.class)).createObjectFromJSON(Entity.class, gameJSON);
    }

    /**
     * Loads in the files from Firebase that are actually needed for a full game to be able to run
     * @param images is a {@JSONObject} that represents the map obtained from the game's images.json file
     */
    private void loadGameImages(JSONObject images) {
        FileStorageConnector connector = new FileStorageConnector();
        FileDataManager manager = new FileDataManager(FileDataFolders.IMAGES);
        List<Object> imageNames =  ((JSONArray) images.get("images")).toList();
        for(Object image : imageNames){
            String name = (String) image;
            byte[] imageBytes = FileConverter.convertImageToByteArray(connector.retrieveImage(name));
            manager.writeFileData(imageBytes, name);
        }
    }

    /**
     * @return An {@code Entity} that represents the root of the game
     */
    public Entity loadGame() { return gameRoot; }
}

package database;

import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import org.json.JSONObject;
import util.PropertiesReader;

import java.io.File;

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
        JSONObject gameJSON = manager.readJSONFile(gameName + "/root.json");
        gameRoot = (new JSONToObjectConverter<Entity>(Entity.class)).createObjectFromJSON(Entity.class, gameJSON);
    }

    /**
     * @return An {@code Entity} that represents the root of the game
     */
    public Entity getRoot() { return gameRoot; }
}

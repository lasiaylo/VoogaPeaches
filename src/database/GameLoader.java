package database;

import com.google.firebase.database.*;
import database.filehelpers.FileConverter;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import database.firebase.DatabaseConnector;
import database.firebase.FileStorageConnector;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.channels.CompletionHandler;
import java.util.List;
import java.util.function.Consumer;

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
     * @param uid is a {@code String} representing the name to
     *                 load from the database
     */
    public GameLoader(String uid, Consumer<Entity> callback) {
        FirebaseDatabase.getInstance().getReference("games").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseConnector<Entity> connector = new DatabaseConnector<>(Entity.class);
                Entity tempRoot = connector.convertDataSnapshotToObject(dataSnapshot);
                JSONObject rootObject = new JSONObject(JSONHelper.JSONForObject(tempRoot).toString().replace("|","/"));
                JSONToObjectConverter<Entity> converter = new JSONToObjectConverter<>(Entity.class);
                gameRoot = converter.createObjectFromJSON(Entity.class, rootObject);

                callback.accept(gameRoot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //JSONDataManager manager = new JSONDataManager(JSONDataFolders.GAMES);
        //JSONObject imageJSON = manager.readJSONFile(gameName + "/images.json");
        //loadGameImages(imageJSON);
        //JSONObject gameJSON = manager.readJSONFile(gameName + "/root.json");
        //gameRoot = (new JSONToObjectConverter<Entity>(Entity.class)).createObjectFromJSON(Entity.class, gameJSON);
    }

    /**
     * Loads in the files from Firebase that are actually needed for a full game to be able to run
     * @param images is a {@JSONObject} that represents the map obtained from the game's images.json file
     */
    private void loadGameImages(JSONObject images) {
        FileStorageConnector connector = new FileStorageConnector("images");
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

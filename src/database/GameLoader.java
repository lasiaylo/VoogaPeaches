package database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import database.filehelpers.FileDataFolders;
import database.filehelpers.FileDataManager;
import database.fileloaders.ScriptLoader;
import database.firebase.DatabaseConnector;
import database.firebase.FileStorageConnector;
import database.jsonhelpers.JSONHelper;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import org.json.JSONObject;

import java.util.ArrayList;
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
    private boolean[] loaded;
    private String uid;

    /**
     * Creates a new GameLoader for loading a game from the database
     * and goes ahead and loads in the game's root
     * @param uid is a {@code String} representing the name to
     *                 load from the database
     */
    public GameLoader(String uid) {
        loaded = new boolean[3];
        this.uid = uid;
    }

    public void loadInAssets() {
        loadGameScripts(uid);
        loadGameImages(uid);
    }

    public void loadInRoot(){
        loadGameRoot(uid);
    }

    public boolean assetsLoadedIn() {
        return loaded[1] && loaded[2];
    }

    private void loadGameRoot(String uid) {
        FirebaseDatabase.getInstance().getReference(uid).child("root").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseConnector<Entity> connector = new DatabaseConnector<>(Entity.class);
                Entity tempRoot = connector.convertDataSnapshotToObject(dataSnapshot);
                JSONObject rootObject = new JSONObject(JSONHelper.JSONForObject(tempRoot).toString().replace("|","/"));
                JSONToObjectConverter<Entity> converter = new JSONToObjectConverter<>(Entity.class);
                gameRoot = converter.createObjectFromJSON(Entity.class, rootObject);
                loaded[0] = true;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Loads in the files from Firebase that are actually needed for a full game to be able to run
     * @param uid is a {@UID} that represents the map obtained from the game's images.json file
     */
    private void loadGameImages(String uid) {
        FirebaseDatabase.getInstance().getReference(uid).child("images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FileStorageConnector connector = new FileStorageConnector("images");
                FileDataManager manager = new FileDataManager(FileDataFolders.IMAGES);
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    byte[] bytes = connector.retrieveBytes( (String)child.getValue());
                    manager.writeFileData(bytes, (String) child.getValue());
                }
                loaded[1] = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void loadGameScripts(String uid) {
        FirebaseDatabase.getInstance().getReference(uid).child("scripts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> files = (ArrayList<String>) dataSnapshot.getValue();
                FileStorageConnector connector = new FileStorageConnector("scripts");
                FileDataManager manager = new FileDataManager(FileDataFolders.SCRIPTS);
                for(String file : files) {
                    byte[] bytes = connector.retrieveBytes(file);
                    manager.writeFileData(bytes, file);
                    System.out.println(file);
                }
                loaded[2] = true;
                // Cache scripts after loading them in
                ScriptLoader.cache();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public boolean isGameLoaded(){ return loaded[0] && loaded[1] && loaded[2];}

    /**
     * @return An {@code Entity} that represents the root of the game
     */
    public Entity loadGame() { return gameRoot; }
}

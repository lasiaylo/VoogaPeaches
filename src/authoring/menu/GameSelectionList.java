package authoring.menu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.Map;

public class GameSelectionList extends ListView<String> {

    private static final String GAME_NAMES = "gameNames";
    private Map<String, String> gameUIDS;

    public GameSelectionList(double width, double height) {
        gameUIDS = new HashMap<>();
        this.setPrefSize(width,height);
        loadGameList();
        applyStyles();
    }

    private void applyStyles() { }

    private void loadGameList() {
        FirebaseDatabase.getInstance().getReference(GAME_NAMES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(key -> {
                    getItems().add(key.getKey());
                    gameUIDS.put(key.getKey(), (String) key.getValue());
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public String getSelectedUID(){
        if(this.getSelectionModel().getSelectedItem() != null)
            return gameUIDS.get(this.getSelectionModel().getSelectedItem());
        return null;
    }
}
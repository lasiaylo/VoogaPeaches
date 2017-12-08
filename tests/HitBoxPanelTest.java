import authoring.panels.tabbable.HitBoxPanel;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import database.jsonhelpers.JSONToObjectConverter;
import engine.collisions.HitBox;
import engine.entities.Entity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HitBoxPanelTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Read in test entity
        JSONDataManager j = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        JSONToObjectConverter<Entity> m = new JSONToObjectConverter<>(Entity.class);
        Entity readIn = m.createObjectFromJSON(Entity.class, j.readJSONFile("test.json"));

        HitBoxPanel hbPanel = new HitBoxPanel();
        hbPanel.setEntity(readIn);



        Scene s = new Scene(hbPanel.getRegion());
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

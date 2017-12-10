package games.util;

import authoring.panels.tabbable.HitBoxPanel;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import engine.events.KeyPressEvent;
import engine.events.TickEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Gravity extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Read in test entity
        JSONDataManager j = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        JSONToObjectConverter<Entity> m = new JSONToObjectConverter<>(Entity.class);
        Entity readIn = m.createObjectFromJSON(Entity.class, j.readJSONFile("PlayerEntity.json"));
        TickEvent e = new TickEvent(1);



        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1),  (dt) -> {
            e.fire(readIn);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene s = new Scene(readIn.getNodes());
        primaryStage.setMinHeight(400);
        s.setOnKeyPressed(ev -> new KeyPressEvent(ev).fire(readIn));
        primaryStage.setMinWidth(400);
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

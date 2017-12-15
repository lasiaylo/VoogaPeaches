package games.util;

import database.fileloaders.ScriptLoader;
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
        ScriptLoader.cache();
        JSONDataManager j = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        JSONToObjectConverter<Entity> m = new JSONToObjectConverter<>(Entity.class);
        Entity readIn = m.createObjectFromJSON(Entity.class, j.readJSONFile("Obstacle.json"));
        TickEvent e = new TickEvent(40);
        System.out.println(readIn);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40), (dt) -> {
            e.fire(readIn);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene s = new Scene(readIn.getNodes());
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(800);
        s.setOnKeyPressed(ev -> new KeyPressEvent(ev).fire(readIn));
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

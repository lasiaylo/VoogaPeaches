import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.collisions.HitBox;
import engine.entities.Entity;
import engine.events.CollisionEvent;
import engine.events.TickEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class BouncingEntities extends Application {
    private TickEvent tickEvent = new TickEvent(1.  / 60);

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<Double> triangleOffsets = new ArrayList<>();
        triangleOffsets.add(-50.0);
        triangleOffsets.add(-50.0);
        triangleOffsets.add(50.0);
        triangleOffsets.add(-50.0);
        triangleOffsets.add(0.0);
        triangleOffsets.add(50.0);

        List<Double> otherOffsets = new ArrayList<>();
        otherOffsets.add(-50.0);
        otherOffsets.add(-50.0);
        otherOffsets.add(-50.0);
        otherOffsets.add(50.0);
        otherOffsets.add(50.0);
        otherOffsets.add(50.0);
        otherOffsets.add(50.0);
        otherOffsets.add(-50.0);


        HitBox triangle = new HitBox(triangleOffsets, 20.0, 20.0, "triangle");
        HitBox square = new HitBox(otherOffsets, 250.0, 250.0, "rectangle");

        JSONDataManager manager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        JSONObject blueprint = manager.readJSONFile("test");
        JSONToObjectConverter<Entity> converter = new JSONToObjectConverter<>(Entity.class);

        Entity triangleEntity = converter.createObjectFromJSON(Entity.class,blueprint);
        Entity squareEntity = converter.createObjectFromJSON(Entity.class,blueprint);

        triangleEntity.setProperty("x", 20.0);
        triangleEntity.setProperty("y", 20.0);

        squareEntity.setProperty("x", 250.0);
        squareEntity.setProperty("y", 250.0);

        squareEntity.setProperty("vx", - (double) squareEntity.getProperty("vx"));
        squareEntity.setProperty("vy", - (double) squareEntity.getProperty("vy"));


        triangleEntity.addHitBox(triangle);
        squareEntity.addHitBox(square);

        Entity root = new Entity();
        triangleEntity.addTo(root);
        squareEntity.addTo(root);

        root.on("tick", e -> {

        });

        Scene s = new Scene(root.getNodes());
        primaryStage.setScene(s);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            tickEvent.recursiveFire(root);
//            tickEvent.fire(triangleEntity);
            for(HitBox triEach : triangleEntity.getHitBoxes()) {
                for(HitBox squareEach : squareEntity.getHitBoxes()) {
                    if(triEach.intersects(squareEach)) {
                        CollisionEvent squareFire = new CollisionEvent(triEach, triangleEntity);
                        CollisionEvent triangleFire = new CollisionEvent(squareEach, squareEntity);
                        squareFire.fire(squareEntity);
                        triangleFire.fire(triangleEntity);
//                        System.out.println("collided");
                    }
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

import database.filehelpers.FileDataManager;
import engine.entities.Entity;
import engine.scripts.defaults.AnimationScript;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class AnimationTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity e = new Entity(new Vector(300, 300), new Vector(10, 10), new Vector(0, 0));
        e.getTransform().setScale(new Vector(3, 3));
        List<Image> imageList = new ArrayList<>();
        FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);

        imageList.add(new Image(manager.readFileData("Duvall/duvall1.jpg")));
        imageList.add(new Image(manager.readFileData("Duvall/duvall2.jpg")));
        imageList.add(new Image(manager.readFileData("Duvall/duvall3.jpg")));
        imageList.add(new Image(manager.readFileData("Duvall/duvall4.jpg")));
        imageList.add(new Image(manager.readFileData("Duvall/duvall5.gif")));

        AnimationScript script = new AnimationScript();
        script.setMyAnimation(imageList);
        e.addScript(script);
        e.getRender().setX(300);
        e.getRender().setY(300);
//        e.update();
        Group group = new Group();
        group.getChildren().add(e.getRender());
//        group.getChildren().add(new ImageView(new Image(manager.readFileData("Background/grass.png"))));
        Scene s = new Scene(group);
        primaryStage.setScene(s);
        primaryStage.show();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 60), event -> e.update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

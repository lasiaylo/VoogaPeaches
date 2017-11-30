import database.filehelpers.FileDataManager;
import engine.entities.Entity;
import engine.managers.CollisionManager;
import engine.managers.HitBox;
import engine.managers.HitBoxCheck;
import engine.scripts.CollisionConditional;
import engine.scripts.defaults.AnimationScript;
import engine.scripts.defaults.DefaultMovement;
import engine.scripts.defaults.XReverse;
import engine.scripts.defaults.YReverse;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class AnimationTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity duvall = new Entity(new Vector(100, 300), new Vector(30, 0), new Vector(0, 0));
//        duvall.getTransform().setScale(new Vector(3, 3));
        List<Image> imageList = new ArrayList<>();
        FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);

        imageList.add(new Image(manager.readFileData("Duvall/duvall1.jpg")));
        imageList.add(new Image(manager.readFileData("Duvall/duvall2.jpg")));
        imageList.add(new Image(manager.readFileData("Duvall/duvall3.jpg")));
        imageList.add(new Image(manager.readFileData("Duvall/duvall4.jpg")));
        imageList.add(new Image(manager.readFileData("Duvall/duvall5.gif")));

        AnimationScript script = new AnimationScript();
        script.setMyAnimation(imageList);
        duvall.addScript(script);
//        e.update();
        Group group = new Group();
        group.getChildren().add(duvall.getRender());
//        group.getChildren().add(new ImageView(new Image(manager.readFileData("Background/grass.png"))));
        Scene s = new Scene(group);
        primaryStage.setScene(s);
        primaryStage.show();

        CollisionManager cManager = new CollisionManager();

        Entity topYBlock = new Entity(new Vector(250, 0));
        Rectangle topYRect = new Rectangle(500, 1);
        topYRect.setFill(Color.TRANSPARENT);
        HitBox topYHitBox = new HitBox(topYRect, topYBlock.getTransform().getPosition(), "top y block", topYBlock.getTransform());

        Entity leftXBlock = new Entity(new Vector(0, 250));
        Rectangle leftXRect = new Rectangle(1, 500);
        topYRect.setFill(Color.TRANSPARENT);
        HitBox leftXHitBox = new HitBox(leftXRect, leftXBlock.getTransform().getPosition(), "left x block", leftXBlock.getTransform());

        Entity botYBlock = new Entity(new Vector(250, 500));
        Rectangle botYRect = new Rectangle(500, 1);
        topYRect.setFill(Color.TRANSPARENT);
        HitBox botYHitBox = new HitBox(botYRect, botYBlock.getTransform().getPosition(), "bot y block", botYBlock.getTransform());

        Entity rightXBlock = new Entity(new Vector(500, 250));
        Rectangle rightXRect = new Rectangle(1, 500);
        topYRect.setFill(Color.TRANSPARENT);
        HitBox rightXHitBox = new HitBox(rightXRect, rightXBlock.getTransform().getPosition(), "right x block", rightXBlock.getTransform());

        Rectangle DuvallHitBox = new Rectangle(duvall.getTransform().getSize().at(0), duvall.getTransform().getSize().at(1));
        HitBox duvallHitBox = new HitBox(DuvallHitBox, duvall.getTransform().getPosition(), "duvall", duvall.getTransform());

        HitBoxCheck checkTop = new HitBoxCheck(duvallHitBox, "top y block");
        HitBoxCheck checkBot = new HitBoxCheck(duvallHitBox, "bot y block");
        HitBoxCheck checkRight = new HitBoxCheck(duvallHitBox, "right x block");
        HitBoxCheck checkLeft = new HitBoxCheck(duvallHitBox, "left x block");

        CollisionConditional hitTop = new CollisionConditional(cManager, checkTop);
        hitTop.getScripts().add(new YReverse());
        hitTop.getScripts().add(new DefaultMovement());
        CollisionConditional hitBot = new CollisionConditional(cManager, checkBot);
        hitBot.getScripts().add(new YReverse());
        hitBot.getScripts().add(new DefaultMovement());

        CollisionConditional hitLeft = new CollisionConditional(cManager, checkLeft);
        hitLeft.getScripts().add(new XReverse());
        hitLeft.getScripts().add(new DefaultMovement());

        CollisionConditional hitRight = new CollisionConditional(cManager, checkRight);
        hitRight.getScripts().add(new XReverse());
        hitRight.getScripts().add(new DefaultMovement());


        topYRect.setX(0);
        topYRect.setY(0);
//        if(topYRect.intersects(DuvallHitBox.getBoundsInLocal())) {
//            System.out.println("hell yeah");
//            System.out.println(DuvallHitBox.getX());
//        }

        DuvallHitBox.setX(100);
        DuvallHitBox.setY(300);
        rightXRect.setX(500);
        rightXRect.setY(0);
        botYRect.setX(0);
        botYRect.setY(500);
        leftXRect.setX(0);
        leftXRect.setY(0);

        duvall.addScript(hitTop);
        duvall.addScript(hitBot);
        duvall.addScript(hitLeft);
        duvall.addScript(hitRight);

        cManager.addHitBox(topYHitBox);
        cManager.addHitBox(rightXHitBox);
        cManager.addHitBox(botYHitBox);
        cManager.addHitBox(leftXHitBox);
        group.getChildren().addAll(topYRect, leftXRect, botYRect, rightXRect);


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 60), event -> {
            duvall.update();
            topYBlock.update();
            rightXBlock.update();
            botYBlock.update();
            leftXBlock.update();
            System.out.println(duvall.getTransform().getVelocity().at(0));
        }));
//        System.out.println(topYRect.getX());
//        System.out.println(botYRect.getY());
//        System.out.println(DuvallHitBox.getWidth());
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

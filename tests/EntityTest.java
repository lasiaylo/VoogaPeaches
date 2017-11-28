import engine.entities.Entity;
import engine.managers.CollisionManager;
import engine.managers.HitBox;
import engine.managers.HitBoxCheck;
import engine.scripts.CollisionConditional;
import engine.scripts.defaults.PrintCollisionScript;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.math.num.Vector;

public class EntityTest extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Entity e = new Entity(new Vector(0, 0));
//        e.addScript(new ReverseDirection());
//        for(int i = 0; i < 10; i++ ) {
//            System.out.println(e.getTransform().getPosition().at(1));
//            e.update();
//            System.out.println(e.getTransform().getPosition().at(1));
//        }
        CollisionManager cManager = new CollisionManager();
        Rectangle rect = new Rectangle(20, 50);
        rect.setX(100);
        rect.setY(100);
        HitBox rectHitBox = new HitBox(rect, new Vector(110, 130), "I am a rectangle");
        HitBoxCheck hBoxCheck = new HitBoxCheck(rectHitBox, "circle check");
        cManager.addHitBox(rectHitBox);

        Circle circle = new Circle(30);
        circle.setCenterX(100);
        circle.setCenterY(100);
        HitBox circleHitBox = new HitBox(circle, new Vector(100, 100), "circle check");
        HitBoxCheck circleBoxCheck = new HitBoxCheck(circleHitBox, "should not be used");
        cManager.addHitBox(circleHitBox);
        PrintCollisionScript printScript = new PrintCollisionScript();

        CollisionConditional cConditional = new CollisionConditional(cManager, hBoxCheck);

        cConditional.getScripts().add(printScript);



        Group group = new Group();
        group.getChildren().addAll(rect, circle);
        Scene s = new Scene(group);
        primaryStage.setScene(s);
        primaryStage.show();

        e.addScript(cConditional);
        e.update();
        System.out.println("end");
    }
}

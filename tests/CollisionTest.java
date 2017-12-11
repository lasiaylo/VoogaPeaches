import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.math.num.Vector;

public class CollisionTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Vector fPos = new Vector(20, 20);
        Vector sPos = new Vector(100, 80);

        Vector fVel = new Vector(2, 2);
        Vector sVel = new Vector(0, 0);

        Shape fShape = new Rectangle(20, 30);
        Shape sShape = new Circle(30);

        HitBox h1 = new HitBox(fPos, fVel, fShape);
        HitBox h2 = new HitBox(sPos, sVel, sShape);
        Circle circle = new Circle();

        Group group = new Group();
        group.getChildren().addAll(h1.getShape(), h2.getShape());
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 60), event -> {
            h1.move((double) 5 / 60);
            h2.move((double) 5 / 60);
            if(h1.intersects(h2)) {
//                h1.setVelocity(new Vector(-h1.getVelocity().at(0), h1.getVelocity().at(1)));
                h2.setVelocity(h1.getVelocity());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private class HitBox {
        private Vector myVelocity;
        private Vector myPosition;
        private Shape myShape;
        public HitBox(Vector position, Vector vel, Shape shape) {
            myPosition = position;
            myVelocity = vel;
            myShape = shape;
            setPosition(myPosition);
        }

        public Vector getVelocity() {
            return myVelocity;
        }

        public void setVelocity(Vector velocity) {
            myVelocity = velocity;
        }

        public Shape getShape() {
            return myShape;
        }

        public boolean intersects(HitBox other) {
            return this.getShape().intersects(other.getShape().getBoundsInLocal());
        }

        public void move(double time) {
            myPosition = myPosition.add(myVelocity.multiply(time));
            setPosition(myPosition);
        }

        private void setPosition(Vector position) {
            try {
                Rectangle rect = (Rectangle) myShape;
                rect.setX(position.at(0));
                rect.setY(position.at(1));

            } catch(Exception e) {

            }
            try {
                Circle circle = (Circle) myShape;
                circle.setCenterX(position.at(0));
                circle.setCenterY(position.at(1));
            } catch(Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

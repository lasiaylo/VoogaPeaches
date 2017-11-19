import authoring.fsm.Arrow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.math.num.Vector;

public class ArrowRenderTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Vector origin = new Vector(50, 50);
        Vector destination = new Vector(20, 59);
        Arrow arrow = new Arrow(origin, destination);
        arrow.setHead(new Vector(450, 127));
        Scene s = new Scene(arrow.getGroup());
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

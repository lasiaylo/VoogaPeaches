import authoring.fsm.StateRender;
import engine.fsm.State;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StateRenderTest extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StateRender sRender = new StateRender(2, 2, "hell yeah", new State());
//        Pane pane = sRender.getArrow();
//        Scene s = new Scene(pane);
//        primaryStage.setScene(s);
//        primaryStage.show();
    }
}

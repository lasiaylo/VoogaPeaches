import authoring.fsm.StateRender;
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
        StateRender sRender = new StateRender(2, 2, "hell yeah");
//        Pane pane = sRender.getRender();
//        Scene s = new Scene(pane);
//        primaryStage.setScene(s);
//        primaryStage.show();
    }
}

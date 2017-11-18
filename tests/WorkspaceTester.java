import authoring.PanelController;
import authoring.panels.PanelManager;
import authoring.workspaces.LeftCameraWorkspace;
import authoring.workspaces.Workspace;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class WorkspaceTester extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Workspace ws = new LeftCameraWorkspace(400, 400, new PanelManager(new PanelController()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch();
    }
}
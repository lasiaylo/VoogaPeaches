package authoring;

import authoring.panels.PanelManager;
import authoring.panels.reserved.CameraPanel;
import authoring.panels.reserved.MenuBarPanel;
import authoring.workspaces.LeftCameraWorkspace;
import authoring.workspaces.Workspace;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.ErrorDisplay;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Screen contains the display of the VoogaPeaches authoring environment. It has a Menu Bar and a Workspace. The workspace is highly customizable, and many different workspaces can be created to suit the user's preference in the display of the various Panels on the screen. The Screen also handles any errors that arise from loading the panels and workspaces. Most errors are non-fatal and result in failure to load a single Panel or Workspace, but if the Screen cannot find the location of any Panels or Workspaces, the program will exit.
 * @author Brian Nieves
 */
public class Screen {

    private VBox root;
    private Workspace workspace;

    private PanelController controller;

    private ResourceBundle properties = ResourceBundle.getBundle("screenlayout"); //If this doesn't work, mark the data folder as a resource folder
    private ResourceBundle panelStrings = ResourceBundle.getBundle("paneldata");
    private ErrorDisplay errorMessage = new ErrorDisplay(panelStrings.getString("errortitle"));

    /**
     * Creates a new Screen and adds it to the stage after population. The size of the Screen is determined by the user's computer screen size.
     * @param stage the stage to add the Screen to
     */
    public Screen(Stage stage){
        root = new VBox();
        controller = new PanelController();


        //SceenBounds Code courtesy of <a href = "http://www.java2s.com/Code/Java/JavaFX/GetScreensize.htm">java2s</a>
        Rectangle2D primaryScreenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        setupStage(stage, primaryScreenBounds);
        int width = (int) primaryScreenBounds.getWidth();
        int height = (int) primaryScreenBounds.getHeight();

        createWorkspace(width, height);

        setupScreen(width);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates a workspace to be added to the Screen. //TODO: Create another workspace and allow the user to choose via the MenuBar.
     * @param width the width of the workspace
     * @param height the height of the workspace
     */
    private void createWorkspace(int width, int height) {
        workspace = null;
        try {
            workspace = new LeftCameraWorkspace(width, height, new PanelManager(controller, errorMessage));
        } catch (FileNotFoundException e) {
            errorMessage.addMessage(panelStrings.getString("nopath"));
            quitOnError();
        } catch (IOException e){
            errorMessage.addMessage(String.format(panelStrings.getString("IOerror"), e.getMessage()));
            quitOnError();
        }
    }

    /**
     * Displays the accumulated errors and terminates the program due to a fatal error.
     */
    private void quitOnError(){
        errorMessage.displayError();
        Platform.exit();
    }

    /**
     * Sets up the Screen by creating the Menu Bar and the Camera. Then adds the camera's display Region to the workspace and adds all the elements to the Screen.
     * @param width the width of the Screen, used to scale the camera appropriately.
     */
    private void setupScreen(int width) {
        double cameraWidthRatio = getDoubleValue("camerawidthscale");
        double cameraWidth = width * cameraWidthRatio;
        double cameraHeight = cameraWidth * getDoubleValue("cameraheighttowidthratio");

        CameraPanel camera = new CameraPanel(cameraWidth, cameraHeight);
        camera.setController(controller);
        MenuBarPanel bar = new MenuBarPanel();
        bar.setController(controller);

        Region cameraRegion = camera.getRegion();
        cameraRegion.setMinWidth(0);
        cameraRegion.setMinHeight(0);
        workspace.addCameraPanel(cameraRegion);
        root.getChildren().addAll(bar.getRegion(), workspace.getWorkspace());
    }

    /**
     * Sets the stage to be maximized.
     * @param stage the stage
     * @param primaryScreenBounds the computer screen's bounds
     */
    private void setupStage(Stage stage, Rectangle2D primaryScreenBounds) {
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
    }

    public void save(){
        //TODO: allow workspace, engine to save to local/database
    }

    /**
     * Returns a double from the screenlayout properties file.
     * @param key the property
     * @return the property's double value
     */
    private double getDoubleValue(String key) {
        return Double.parseDouble(properties.getString(key));
    }

}
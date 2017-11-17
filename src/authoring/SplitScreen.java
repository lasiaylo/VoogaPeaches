package authoring;

import authoring.panels.reserved.CameraPanel;
import authoring.panels.reserved.MenuBarPanel;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Screen represents the container of the various areas of the authoring environment user interface. Areas can contain multiple Panels, and each Panel specifies what area it should be viewed in.
 * @author Brian Nieves
 * @author estellehes
 */
public class SplitScreen {

    private VBox root;
    private SplitPane body;
    private SplitPane middle;
    private PanelController controller;

    private ResourceBundle properties = ResourceBundle.getBundle("screenlayout"); //If this doesn't work, mark the data folder as a resource folder
    private ResourceBundle panelStrings = ResourceBundle.getBundle("screenerrors");
    private StringBuilder errorMessage = new StringBuilder();

    public SplitScreen(Stage stage){

        root = new VBox();
        body = new SplitPane();
        middle = new SplitPane();
        controller = new PanelController();

        body.setOrientation(Orientation.VERTICAL);
        middle.setOrientation(Orientation.HORIZONTAL);

        /*
         * Code courtesy of <a href = "http://www.java2s.com/Code/Java/JavaFX/GetScreensize.htm">java2s</a>
         */
        Rectangle2D primaryScreenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        setupStage(stage, primaryScreenBounds);

        int width = (int) primaryScreenBounds.getWidth();
        int height = (int) primaryScreenBounds.getHeight();
        double cameraWidthRatio = getDoubleValue("camerawidthscale");
        double cameraWidth = width * cameraWidthRatio;
        double cameraHeight = cameraWidth * getDoubleValue("cameraheighttowidthratio");

        TabPane bottom = new TabPane();
        TabPane side = new TabPane();

        CameraPanel camera = new CameraPanel(cameraWidth, cameraHeight);
        camera.getRegion().setMinWidth(0);
        camera.getRegion().setMinHeight(0);

        middle.getItems().addAll(camera.getRegion(), side);

        body.getItems().addAll(middle, bottom);

        MenuBarPanel bar = new MenuBarPanel();
        bar.setController(controller);
        root.getChildren().addAll(bar.getRegion(), body);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    private void setupStage(Stage stage, Rectangle2D primaryScreenBounds) {
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
    }

    private double getDoubleValue(String key) {
        return Double.parseDouble(properties.getString(key));
    }

}
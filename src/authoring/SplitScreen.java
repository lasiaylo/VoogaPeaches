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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.fusesource.jansi.Ansi;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private TabPane bottom;
    private TabPane side;

    private PanelController controller;

    private ResourceBundle properties = ResourceBundle.getBundle("screenlayout"); //If this doesn't work, mark the data folder as a resource folder
    private ResourceBundle panelStrings = ResourceBundle.getBundle("screenerrors");
    private StringBuilder errorMessage = new StringBuilder();

    public SplitScreen(Stage stage){
        initialize();

        //SceenBounds Code courtesy of <a href = "http://www.java2s.com/Code/Java/JavaFX/GetScreensize.htm">java2s</a>
        Rectangle2D primaryScreenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        setupStage(stage, primaryScreenBounds);
        int width = (int) primaryScreenBounds.getWidth();
        int height = (int) primaryScreenBounds.getHeight();
        setupScreen(width, height);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    private void setupScreen(int width, int height) {
        double cameraWidthRatio = getDoubleValue("camerawidthscale");
        double cameraWidth = width * cameraWidthRatio;
        double cameraHeight = cameraWidth * getDoubleValue("cameraheighttowidthratio");

        body.setOrientation(Orientation.VERTICAL);
        middle.setOrientation(Orientation.HORIZONTAL);
        CameraPanel camera = new CameraPanel(cameraWidth, cameraHeight);
        camera.setController(controller);

        middle.getItems().addAll(camera.getRegion(), side);
        middle.setDividerPosition(0, getDoubleValue("middledivision"));

        body.getItems().addAll(middle, bottom);
        body.setDividerPosition(0, getDoubleValue("bodydivision"));

        MenuBarPanel bar = new MenuBarPanel();
        bar.setController(controller);


        camera.getRegion().setMinWidth(0);
        camera.getRegion().setMinHeight(0);
        side.setMinWidth(0);
        middle.setMinWidth(width);
        middle.setMinHeight(0);
        body.setMinHeight(height);

        root.getChildren().addAll(bar.getRegion(), body);
    }

    private void initialize() {
        root = new VBox();
        body = new SplitPane();
        middle = new SplitPane();
        controller = new PanelController();
        bottom = new TabPane();
        side = new TabPane();
    }

    private void setupStage(Stage stage, Rectangle2D primaryScreenBounds) {
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
    }

    private List<Panel> getPanels(){
        List<Panel> panels = new ArrayList<>();
        try {
            File panelsFolder = new File(panelStrings.getString("tabpath"));
            File[] panelFiles = panelsFolder.listFiles();
            String[] names = new String[panelFiles.length-1];
            for(int i = 0; i < panelFiles.length; i++){
                names[i] = panelFiles[i].getName();
            }

            String qualifier = panelStrings.getString("classqualifier");

            for(String panelName : names){
                try {
                    Class<Panel> panelClass = (Class<Panel>) Class.forName(qualifier + panelName.replace(".java", ""));
                    Constructor<Panel> panelConstructor = panelClass.getConstructor();
                    Panel panel = panelConstructor.newInstance();
                    panel.setController(controller);
                    panels.add(panel);
                } catch (ClassNotFoundException e) {
                    errorMessage.append(String.format(panelStrings.getString("nopanel"), e.getMessage()));
                } catch (NoSuchMethodException e) {
                    errorMessage.append(String.format(panelStrings.getString("noconstructor"), e.getMessage()));
                } catch (InstantiationException e) {
                    errorMessage.append(String.format(panelStrings.getString("noinstance"), e.getMessage()));
                } catch (IllegalAccessException e) {
                    errorMessage.append(String.format(panelStrings.getString("hiddenconstructor"), e.getMessage()));
                } catch (InvocationTargetException e) {
                    errorMessage.append(String.format(panelStrings.getString("noinvocation"), e.getMessage()));
                }
            }

            return panels;

        } catch (NullPointerException e){
            errorMessage.append(panelStrings.getString("nopath"));
            displayError();
            Platform.exit();
        }
        return panels;
    }

    private void displayError() {
        if(errorMessage.length() > 0) {
            Alert errors = new Alert(Alert.AlertType.ERROR);
            errors.setTitle(panelStrings.getString("errortitle"));
            errors.setContentText(errorMessage.toString());

            errors.showAndWait();
        }
    }

    private double getDoubleValue(String key) {
        return Double.parseDouble(properties.getString(key));
    }

}
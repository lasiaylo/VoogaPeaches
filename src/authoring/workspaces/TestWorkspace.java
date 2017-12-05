package authoring.workspaces;

import authoring.Positions;
import authoring.TabManager;
import authoring.Workspace;
import authoring.panels.PanelManager;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class TestWorkspace implements Workspace {

    private SplitPane body;
    private SplitPane middle;
    private TabPane bottom;
    private TabPane right;
    private double middleDivision = 0.7; //Default value
    private double bodyDivision = 0.7; //Default value
    private final Positions positions = new Positions("right", "bottom");
    private Map<String, Positions.Position> panelPositions;
    private PanelManager manager;
    private TabManager tabManager;

    public TestWorkspace(double width, double height, PanelManager panelManager){
        panelPositions = new HashMap<>();
        body = new SplitPane();
        middle = new SplitPane();
        bottom = positions.getPosition("bottom").getPane();
        right = positions.getPosition("right").getPane();
        tabManager = new TabManager(positions);

        body.setOrientation(Orientation.VERTICAL);
        middle.setOrientation(Orientation.HORIZONTAL);

        Pane cameraDummy = new Pane();

        middle.getItems().addAll(cameraDummy, right);
        middle.setDividerPosition(0, middleDivision);

        body.getItems().addAll(middle, bottom);
        body.setDividerPosition(0, bodyDivision);
        right.setMinWidth(0);
        middle.setMinWidth(width);
        middle.setMinHeight(0);
        body.setMinHeight(height);
    }

    @Override
    public Region getWorkspace() {
        Tab one = tabManager.newTab("Tab Number One");
        Tab two = tabManager.newTab("Tab Number Two");
        Tab three = tabManager.newTab("Tab Number Three");
        Tab four = tabManager.newTab("Tab Number Four");
        right.getTabs().addAll(one, two, three);
        right.setOnKeyPressed(e -> right.getTabs().add(right.getTabs().remove(0)));
        return body;
    }

    @Override
    public void addCameraPanel(Region cameraPanel) {
        middle.getItems().set(0, cameraPanel);
    }
}

package authoring.panels.tabbable;

import authoring.fsm.FSMGraph;
import authoring.panels.attributes.UpdatablePanel;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class FSMPanel implements UpdatablePanel {

    private final String TITLE = "FSM Panel";
    private FSMGraph graph;

    public FSMPanel() {
        graph = new FSMGraph();
    }

    @Override
    public void updateProperties() {

    }

    @Override
    public Region getRegion() {
        Button button = new Button("FSM");
        button.setMinHeight(50);
        button.setMinWidth(50);

        Pane pane = new Pane(graph.getRender());
        VBox box = new VBox(button, pane);
        box.setFillWidth(true);
        box.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
        pane.minHeightProperty().bind(box.heightProperty());
        pane.setOnMouseClicked(graph::onSceneClick);
        return box;
    }

    @Override
    public String title() {
        return TITLE;
    }
}

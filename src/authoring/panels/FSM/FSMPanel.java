package authoring.panels.FSM;

import authoring.fsm.FSMGraph;
import authoring.panels.attributes.UpdatablePanel;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class FSMPanel implements UpdatablePanel {

    private final String TITLE = "Properties";
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

        Pane pane = new Pane(graph.getRender());

        pane.setOnMouseClicked(graph::onSceneClick);
        VBox box = new VBox(button, pane);



        return button;
    }

    @Override
    public String title() {
        return TITLE;
    }
}

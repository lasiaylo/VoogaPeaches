package authoring.panels.tabbable;

import authoring.fsm.FSMGraph;
import authoring.panels.attributes.UpdatablePanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        VBox box = new VBox(button);
        box.setFillWidth(true);
        button.setOnMouseClicked(e -> createNewFSM());
        return box;
    }

    private void createNewFSM() {
        Stage stage = new Stage();
        FSMGraph graph = new FSMGraph();
        Scene s = new Scene(graph.getRender());
        s.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> graph.onSceneClick(e));
        stage.setScene(s);
        stage.show();
    }

    @Override
    public String title() {
        return TITLE;
    }
}

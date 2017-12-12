package authoring.panels.tabbable;

import authoring.fsm.FSMGraph;
import authoring.panels.attributes.UpdatablePanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FSMPanel implements UpdatablePanel {

    private final String TITLE = "FSM Panel";
    private VBox box;
    private Stage stage;

    public FSMPanel() {
        Button button = new Button("Add New FSM");
        button.setMinHeight(50);
        button.setMinWidth(50);
        box = new VBox(button);
        box.setFillWidth(true);
        button.setOnMouseClicked(e -> prompt());
        stage = new Stage();
    }

    @Override
    public void updateProperties() { }

    @Override
    public Region getRegion() {
        return box;
    }

    private void prompt() {
        Scene scene = new Scene(new Group());
        FlowPane flow = createPopup();
        scene.setRoot(flow);
        stage.setScene(scene);
        stage.show();
    }

    private FlowPane createPopup() {
        FlowPane flow = new FlowPane();
        flow.setMinSize(100, 200);
        Button start = new Button("Create FSM");
        TextField name = new TextField();
        name.setPromptText("Enter your FSM name");
        start.setOnMouseClicked(e -> createNewFSM(name.getText()));
        flow.getChildren().addAll(start, name);
        return flow;
    }

    private void createNewFSM(String name) {
        FSMGraph graph = new FSMGraph(name);
        Scene s = new Scene(graph.getRender());
        s.addEventFilter(MouseEvent.MOUSE_PRESSED, graph::onSceneClick);
        stage.setTitle(name);
        stage.setScene(s);
        stage.setOnHidden(e -> graph.export());
    }

    @Override
    public String title() {
        return TITLE;
    }
}

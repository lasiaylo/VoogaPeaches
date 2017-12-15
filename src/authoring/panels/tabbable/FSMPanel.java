package authoring.panels.tabbable;

import authoring.Panel;
import authoring.fsm.FSMGraph;
import authoring.panels.attributes.Updatable;
import engine.fsm.FSM;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.pubsub.PubSub;
import util.pubsub.messages.EntityPass;
import util.pubsub.messages.FSMGraphMessage;
import util.pubsub.messages.FSMMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FSMPanel implements Panel, Updatable {

    private final String TITLE = "FSM Panel";
    private VBox box = new VBox();
    private String currentEntity;
    private static Map<String, ArrayList<FSMGraph>> FSMMap = new HashMap<>();

    public FSMPanel() {
        PubSub.getInstance().subscribe("FSM", message -> publishFSM((FSMMessage) message));
        PubSub.getInstance().subscribe("ENTITY_PASS", message -> newEntityClicked((EntityPass) message));
        PubSub.getInstance().subscribe("FSM_GRAPH", message -> saveGraph((FSMGraphMessage) message));
        init();
    }

    public static Map<String, ArrayList<FSMGraph>> getFSMMap() { return FSMMap; }

    public static void setFSMMap(Map<String, ArrayList<FSMGraph>> map) {
        FSMMap = map;
    }

    private void saveGraph(FSMGraphMessage message) {
        ArrayList<FSMGraph> values = FSMMap.getOrDefault(currentEntity, new ArrayList<>());
        if(!values.contains(message.getGraph())) {
            values.add(message.getGraph());
            FSMMap.put(currentEntity, values);
        }
        init();
    }

    private void newEntityClicked(EntityPass message) {
        currentEntity = message.getEntity().UIDforObject();
        init();
    }

    /**
     * Publishes the FSM corresponding to do the correct FSMGraph as decided by the Entity and name in the message
     *
     * @param message
     */
    private void publishFSM(FSMMessage message) {
        System.out.println("trying go fire FSM: " + message.getName());
        if(message.getFSM() != null) { return; }
        if(FSMMap.containsKey(message.getEntity().UIDforObject())){
            for(FSMGraph entry: FSMMap.get(message.getEntity().UIDforObject())) {
                if(entry.getMyName().equals(message.getName())) {
                    System.out.println("published FSM");
                    FSM fsm = entry.createFSM(message.getEntity());
                    if (fsm == null) { return; }
                    PubSub.getInstance().publish("FSM", new FSMMessage(
                            message.getName(),
                            message.getEntity(),
                            fsm));
                }
            }
        }
    }

    private void init() {
        box.getChildren().clear();
        createAddButton();
        createGraphButtons();
    }

    private void createGraphButtons() {
        if (currentEntity == null || FSMMap == null || FSMMap.get(currentEntity) == null) { return; }
        for(FSMGraph graph: FSMMap.get(currentEntity)) {
            Button button = new Button(graph.getMyName());
            button.setMinHeight(50);
            button.setMinWidth(50);
            button.setOnMouseClicked(e -> readGraph(graph));
            box.getChildren().add(button);
        }
    }

    private void readGraph(FSMGraph graph) {
        Stage stage = new Stage();
        Group wrapper = new Group(graph.getRender());
        Scene s = new Scene(wrapper);
        s.addEventFilter(MouseEvent.MOUSE_PRESSED, graph::onSceneClick);
        stage.setTitle(graph.getMyName());
        stage.setScene(s);
        stage.setOnHidden(e -> closeGraph(graph, wrapper));
        stage.show();
    }

    private void closeGraph(FSMGraph graph, Group wrapper) {
        wrapper.getChildren().clear();
        graph.export();
        init();
    }

    private void createAddButton() {
        Button button = new Button("Add New FSM");
        button.setMinHeight(50);
        button.setMinWidth(50);
        button.setOnMouseClicked(e -> prompt());
        box.getChildren().add(button);
    }

    @Override
    public Region getRegion() {
        return box;
    }

    private void prompt() {
        Stage stage = new Stage();
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
        start.setOnMouseClicked(e -> createNewFSM(name.getText(), start));
        flow.getChildren().addAll(start, name);
        return flow;
    }

    private void createNewFSM(String name, Button start) {
        ((Stage) start.getScene().getWindow()).close();
        if (currentEntity == null) {
            createError();
            return;
        }
        FSMGraph graph = new FSMGraph(name);
        readGraph(graph);
    }

    private void createError() {
    }

    @Override
    public String title() {
        return TITLE;
    }

    @Override
    public void update() { }
}

package authoring.panels.tabbable;

import authoring.Panel;
import authoring.fsm.FSMGraph;
import authoring.panels.attributes.Updatable;
import engine.entities.Entity;
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
import util.pubsub.messages.FSMMessage;
import util.pubsub.messages.FSMSaveMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FSMPanel implements Panel, Updatable {

    private final String TITLE = "FSM Panel";
    private VBox box = new VBox();
    private Entity currentEntity;
    private Map<Entity, List<FSMGraph>> myMap = new HashMap<>();

    public FSMPanel() {
        init();
        PubSub.getInstance().subscribe("FSM", message -> readMessage((FSMMessage) message));
        PubSub.getInstance().subscribe("ENTITY_PASS", message -> newEntityClicked((EntityPass) message));
        PubSub.getInstance().subscribe("SAVE_FSM", message -> saveRequested((FSMSaveMessage) message));
    }

    private void saveRequested(FSMSaveMessage message) {
        if(message.getFSMmap() == null) {
            PubSub.getInstance().publish("SAVE_FSM", new FSMSaveMessage(myMap));
        }
    }

    private void newEntityClicked(EntityPass message) {
        currentEntity = message.getEntity();
        init();
    }

    /**
     * Publishes the FSM corresponding to do the correct FSMGraph as decided by the Entity and name in the message
     *
     * @param message
     */
    private void readMessage(FSMMessage message) {
        if(myMap.containsKey(message.getEntity()) && myMap.get(message.getEntity()).contains(message.getName())){
            for(FSMGraph entry: myMap.get(message.getEntity())) {
                if(entry.getMyName().equals(message.getName())) {
                    PubSub.getInstance().publish("FSM",
                            new FSMMessage(message.getName(),
                                    message.getEntity(),
                                    entry.createFSM(message.getEntity())));
                }
            }
        }
    }

    private void init() {
        box.getChildren().clear();
        createGraphs();
        createAddButton();
        createGraphButtons();
    }

    private void createGraphButtons() {
        for(FSMGraph graph: myMap.get(currentEntity)) {
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

    private void createGraphs() {
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
        FSMGraph graph = new FSMGraph(name);
        readGraph(graph);
    }

    @Override
    public String title() {
        return TITLE;
    }

    @Override
    public void update() {

    }
}

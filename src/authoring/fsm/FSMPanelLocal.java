package authoring.fsm;

import authoring.panels.attributes.Updatable;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FSMPanelLocal implements Updatable {

    private static final String FSM_PATH = "data/jsondata/fsm";
    private VBox box = new VBox();
    private List<FSMGraph> allGraphs;
    private Map<Entity, List<FSMGraph>> myMap = new HashMap<>();

    public FSMPanelLocal() {
        init();
        PubSub.getInstance().subscribe("FSM", message -> readMessage((FSMMessage) message));
        PubSub.getInstance().subscribe("ENTITY_PASS", message -> newEntityClicked((EntityPass) message));
    }

    private void newEntityClicked(EntityPass message) {
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
        allGraphs = new ArrayList<>();
        box.getChildren().clear();
        createGraphs();
        createAddButton();
        createGraphButtons();
    }

    private void createGraphButtons() {
        for(FSMGraph graph: allGraphs) {
            System.out.println(graph.getMyName());
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
        File folder = new File(FSM_PATH);
        File[] listOfFiles = folder.listFiles();
        JSONDataManager j = new JSONDataManager(JSONDataFolders.FSM);
        JSONToObjectConverter<FSMGraph> m = new JSONToObjectConverter<>(FSMGraph.class);
        for (File file : listOfFiles) {
            FSMGraph graph = m.createObjectFromJSON(FSMGraph.class, j.readJSONFile(file.getName()));
            allGraphs.add(graph);
        }
    }

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
    public void update() {

    }
}

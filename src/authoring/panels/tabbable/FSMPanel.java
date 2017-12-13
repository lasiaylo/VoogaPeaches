package authoring.panels.tabbable;

import authoring.fsm.FSMGraph;
import authoring.panels.attributes.UpdatablePanel;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FSMPanel implements UpdatablePanel {

    private static final String FSM_PATH = "data/jsondata/fsm";
    private final String TITLE = "FSM Panel";
    private VBox box = new VBox();
    private List<FSMGraph> allGraphs;
    private Map<Entity, List<FSMGraph>> myMap = new HashMap<>();

    public FSMPanel() {
        init();
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
            System.out.println(file.getName());
            FSMGraph graph = m.createObjectFromJSON(FSMGraph.class, j.readJSONFile(file.getName()));
            allGraphs.add(graph);
        }
    }

    @Override
    public void updateProperties() { }

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
}

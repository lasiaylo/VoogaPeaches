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

/**
 * A panel to create the FSM. On the click of an entity, the past FSMs attached to the property panel show up and
 * allow the user to create their own new FSMs. The panel interface is required to be a panel and the updatable
 * interface allows use of the variable inputs like those that are in the state creation in the FSM and properties
 * panel. Users declare the type and name of their variable, then their value.
 *
 * FSMMap is static because it needs to be loaded from the database, but this connection to the database is done
 * by the GameLoader and the GameLoader is deleted before the creation of the panels. To avoid massive and ugly
 * workarounds, the map was made static.
 *
 * @author Simran
 */
public class FSMPanel implements Panel, Updatable {

    private static Map<String, ArrayList<FSMGraph>> FSMMap = new HashMap<>();
    private static final String FSM_PROMPT = "Enter your FSM name";
    private static final String CREATE_FSM = "Create FSM";
    private final String TITLE = "FSM Panel";
    private final String ADD_BUTTON = "Add New FSM";
    private VBox box = new VBox();
    private String currentEntity;

    /**
     * Subscriptions to PubSub set up and the buttons are initialized.
     */
    public FSMPanel() {
        PubSub.getInstance().subscribe("FSM", message -> publishFSM((FSMMessage) message));
        PubSub.getInstance().subscribe("ENTITY_PASS", message -> newEntityClicked((EntityPass) message));
        PubSub.getInstance().subscribe("FSM_GRAPH", message -> saveGraph((FSMGraphMessage) message));
        init();
    }

    /**
     * @return Returns the region to be viewed in the panel.
     */
    @Override
    public Region getRegion() {
        return box;
    }


    /**
     * @return String to be associated with the Panel
     */
    @Override
    public String title() {
        return TITLE;
    }

    /**
     * Method for Updatable, but there is no need to update in this panel.
     */
    @Override
    public void update() { }

    /**
     * Returns the map to be loaded by the GameSaver.
     */
    public static Map<String, ArrayList<FSMGraph>> getFSMMap() { return FSMMap; }

    /**
     * Loads the map from the GameLoader.
     */
    public static void setFSMMap(Map<String, ArrayList<FSMGraph>> map) {
        FSMMap = map;
    }

    /**
     * Saves the graph into FSMMap and recreates the buttons so that the new FSM shows up in the panel.
     *
     * @param message FSMGraphMessage when an FSM that is being created is closed
     */
    private void saveGraph(FSMGraphMessage message) {
        ArrayList<FSMGraph> values = FSMMap.getOrDefault(currentEntity, new ArrayList<>());
        if(!values.contains(message.getGraph())) {
            values.add(message.getGraph());
            FSMMap.put(currentEntity, values);
        }
        init();
    }

    /**
     * Changes the current entity to the most recently clicked entity.
     *
     * @param message EntityPass message is published anytime an Entity is clicked.
     */
    private void newEntityClicked(EntityPass message) {
        currentEntity = message.getEntity().UIDforObject();
        init();
    }

    /**
     * Publishes the FSM corresponding to do the correct FSMGraph as decided by the Entity and name in the message.
     * An fsm.groovy class attached to an entity publishes itself to "ask" for an FSM. This method returns the FSM
     * to the entity requesting it.
     *
     * @param message Message with an entity and name
     */
    private void publishFSM(FSMMessage message) {
        if(message.getFSM() != null) { return; }
        if(FSMMap.containsKey(message.getEntity().UIDforObject())){
            for(FSMGraph entry: FSMMap.get(message.getEntity().UIDforObject())) {
                if(entry.getMyName().equals(message.getName())) {
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

    /**
     * Recreates add button and buttons associated with the graphs. Used to update the buttons on the panel.
     */
    private void init() {
        box.getChildren().clear();
        createAddButton();
        createGraphButtons();
    }

    /**
     * Creates the buttons associated with a graph. On click it creates a new environment to edit the existing FSM.
     */
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

    /**
     * A wrapper group is necessary because the visualization is not correctly viewed without it for JavaFX reasons.
     * The graph.getRender() can only be bound to one group. This creates problem when you want to reopen an FSM, so
     * a wrapper group is created and then cleared before closing.
     *
     * @param graph Creates the visualization for this existing FSMGraph.
     */
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

    /**
     * @param graph FSMGraph that is closed and then exported
     * @param wrapper The wrapper group that needs to be cleared
     */
    private void closeGraph(FSMGraph graph, Group wrapper) {
        wrapper.getChildren().clear();
        graph.export();
        init();
    }

    /**
     * Creates the add button in the panel.
     */
    private void createAddButton() {
        Button button = new Button(ADD_BUTTON);
        button.setMinHeight(50);
        button.setMinWidth(50);
        button.setOnMouseClicked(e -> prompt());
        box.getChildren().add(button);
    }

    /**
     * Creates the new stage that is shown on an Add Button click.
     */
    private void prompt() {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        FlowPane flow = createPopup();
        scene.setRoot(flow);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @return The FlowPane with the content for the prompt popup on Add Button click
     */
    private FlowPane createPopup() {
        FlowPane flow = new FlowPane();
        flow.setMinSize(100, 200);
        Button start = new Button(CREATE_FSM);
        TextField name = new TextField();
        name.setPromptText(FSM_PROMPT);
        start.setOnMouseClicked(e -> createNewFSM(name.getText(), start));
        flow.getChildren().addAll(start, name);
        return flow;
    }

    /**
     * @param name Name of the new FSMGraph created
     * @param start The button that was pressed to close the window
     */
    private void createNewFSM(String name, Button start) {
        ((Stage) start.getScene().getWindow()).close();
        if (currentEntity == null) { return; }
        FSMGraph graph = new FSMGraph(name);
        readGraph(graph);
    }

}

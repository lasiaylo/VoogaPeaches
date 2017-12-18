package authoring.fsm;

import authoring.panels.attributes.ParameterProperties;
import authoring.panels.attributes.Updatable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import util.PropertiesReader;
import util.exceptions.GroovyInstantiationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper Popup for the StateRender class. Holds all the information for the properties of the state and sets them for
 * the State. Implements Updatable because it uses a similar input mechanism as the Properties Panel.
 *
 * @author Simran
 */
public class StateRenderPopup implements Updatable {

    private Label myLabel;
    private FlowPane flow = new FlowPane();
    private Stage stage;
    private StateRender myStateRender;
    private GraphDelegate myGraph;
    private Map<String,Object> myInfo;

    /**
     * @param graph GraphDelegate for the StateRender
     * @param stateRender The StateRender that this is a popup for
     * @param label The label containing the name for the State
     */
    StateRenderPopup(GraphDelegate graph, StateRender stateRender, Label label) {
        myGraph = graph;
        myStateRender = stateRender;
        myLabel = label;
        Scene scene = new Scene(new Group());
        myInfo = new HashMap();
        update();
        scene.setRoot(flow);
        stage = new Stage();
        stage.setScene(scene);
    }

    /**
     * The code responsible for holding the input parameters in the state visualization. Users input their variables
     * here and they get stored in a map of information.
     */
    @Override
    public void update() {
        flow.getChildren().clear();
        try {
            flow.getChildren().add(new ParameterProperties(myInfo, this).getNode());
        } catch (GroovyInstantiationException e) { }
        flow.setMinSize(100, 200);
        Button delete = new Button(PropertiesReader.value("fsm", "DELETE_STATE"));
        Button save = new Button(PropertiesReader.value("fsm", "DONE"));
        delete.setOnMouseClicked(e -> onDelete(delete));
        save.setOnMouseClicked(e -> onDone(save));
        flow.getChildren().addAll(delete, save, myLabel);
    }

    public Stage getStage() { return stage; }

    /**
     * @param done Deletes the stage popup after closing
     */
    private void onDone(Button done) {
        ((Stage) done.getScene().getWindow()).close();
    }

    /**
     * Removes the StateRender from the graph that holds information about all states and arrows
     *
     * @param delete The delete button needed to close the popup
     */
    private void onDelete(Button delete) {
        ((Stage) delete.getScene().getWindow()).close();
        myGraph.removeMyself(myStateRender);
        myStateRender.setMyInfo(myInfo);
    }
}

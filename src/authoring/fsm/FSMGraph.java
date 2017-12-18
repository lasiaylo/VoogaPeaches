package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import engine.entities.Entity;
import engine.fsm.FSM;
import engine.fsm.State;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import util.PropertiesReader;
import util.math.num.Vector;
import util.pubsub.PubSub;
import util.pubsub.messages.FSMGraphMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles the rendering of the graph of a finite state machine. This class is incredibly long because
 * comments and initializations take a lot of code. There are many basic things that must be done for this class to
 * function properly.
 *
 * @author Albert
 * @author Simran
 */
public class FSMGraph extends TrackableObject implements GraphDelegate {

    @Expose private List<StateRender> myStateRenders;
    @Expose private List<Arrow> myArrows;
    @Expose private String myName;
    private Group myGroup = new Group();
    private Arrow currentArrow;
    private boolean addingState;
    private List<State> myStates;

    /**
     * Creates a new FSMGraph from existing arraylist params
     * @param stateRenders      list of stateRenders
     * @param Arrows list of Arrows
     */
    public FSMGraph(String name, List<StateRender> stateRenders, List<Arrow> Arrows) {
        myName = name;
        myStateRenders = stateRenders;
        myArrows = Arrows;
        myGroup.setOnMouseDragged(e -> dragHandle(e));
        myGroup.setOnMouseReleased(e -> dragExit(e));
    }

    /**
     * Creates a new FSMGraph from just the name
     */
    public FSMGraph(String name) {
        this(name, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Creates new FSMGraph with all defaulted values
     */
    public FSMGraph() { this(PropertiesReader.value("fsm", "NO_NAME_GIVEN")); }

    /**
     * On recreation of Graph from database, this method links all the states and transitions
     */
    @Override
    public void initialize() {
        for(StateRender sRender: myStateRenders) {
            sRender.setGraphDelegate(this);
            myGroup.getChildren().add(sRender.getRender());
        }
        for(Arrow arrow: myArrows) {
            arrow.setGraphDelegate(this);
            myGroup.getChildren().add(arrow.getRender());
        }
        myGroup.setOnMouseDragged(e -> dragHandle(e));
        myGroup.setOnMouseReleased(e -> dragExit(e));
    }

    /**
     * Removes state from group and all associations with the given State
     *
     * @param state The state that is being deleted
     */
    @Override
    public void removeMyself(StateRender state){
        myStateRenders.remove(state);
        myGroup.getChildren().remove(state.getRender());
        for(Arrow arrow:state.getMyLeavingTransitions()){
            removeMyself(arrow);
        }
    }

    /**
     * Removes arrow from the group and the list of leaving transitions from the appropriate StateRender
     *
     * @param arrow The arrow that is being deleted
     */
    @Override
    public void removeMyself(Arrow arrow){
        myArrows.remove(arrow);
        myGroup.getChildren().remove(arrow.getRender());
        for(StateRender state:myStateRenders){
            if(state.getMyLeavingTransitions().contains(arrow)){
                state.removeLeavingTransition(arrow);
            }
        }
    }

    /**
     * @param code The code that an arrow has
     * @return The arrow with the given code
     */
    @Override
    public Arrow findArrowWith(String code) {
        for(Arrow arrow: myArrows) {
            if (arrow.getMyCode().equals(code)) {
                return arrow;
            }
        }
        return null;
    }

    /**
     * @param name Name of the state
     * @return The State Render with the name
     */
    @Override
    public StateRender findStateRenderWith(String name) {
        for(StateRender sRender: myStateRenders) {
            if (sRender.getName().equals(name)) {
                return sRender;
            }
        }
        return null;
    }

    /**
     * @return the name of the FSMGraph
     */
    public String getMyName() {
        return myName;
    }

    /**
     * On the click of the scene containing the FSMGraph render, this creates the popup for the user to start making
     * their FSM
     *
     * @param e The click event
     */
    public void onSceneClick(MouseEvent e) {
        if (validClick(e)) {
            Scene scene = new Scene(new Group());
            FlowPane flow = createPopup(e);
            addingState = true;
            scene.setRoot(flow);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setOnHidden(f -> addingState = false);
            stage.show();
        }
    }

    /**
     * Handles closing of the Graph by creating all the SavedState objects and publishing itself to FSMPanel
     */
    public void export() {
        recreateStates();
        for(StateRender sRender: myStateRenders) {
            sRender.save();
        }
        for(Arrow arrow: myArrows) {
            arrow.save();
        }
        PubSub.getInstance().publish("FSM_GRAPH", new FSMGraphMessage(this));
    }

    /**
     * @param entity The entity associated with the new FSM
     * @return The FSM Object associated with the Graph
     */
    public FSM createFSM(Entity entity) {
        recreateStates();
        return new FSM(entity, myStates);
    }

    /**
     * @return The group of the FSMGraph
     */
    public Group getRender() {
        return myGroup;
    }

    /**
     * @param e The click event
     * @return true if they are not already adding a state and if they did not click in an arrow or existing state
     */
    private boolean validClick(MouseEvent e) {
        return !addingState && findContainedStateRender(e) == null && findContainedArrow(e) == null;
    }

    /**
     * @param e The click event
     * @return FlowPane for popup to create a new State
     */
    private FlowPane createPopup(MouseEvent e) {
        FlowPane flow = new FlowPane();
        flow.setMinSize(100, 200);
        Button newState = new Button(PropertiesReader.value("fsm", "CREATE_NEW_STATE"));
        Button cancel = new Button(PropertiesReader.value("fsm", "CANCEL"));
        TextField name = new TextField();
        name.setPromptText(PropertiesReader.value("fsm", "ENTER_YOUR_NAME"));
        newState.setOnMouseClicked(f -> onCreate(e, newState, name.getText()));
        cancel.setOnMouseClicked(f -> onClose(cancel));
        flow.getChildren().addAll(newState, cancel, name);
        return flow;
    }

    /**
     * Creates a new State
     *
     * @param e Click Event
     * @param newState The button that allows you to create a new state
     * @param name Name of the state
     */
    private void onCreate(MouseEvent e, Button newState, String name){
        StateRender sRender = new StateRender(e.getX(), e.getY(), name, this);
        sRender.onClick();
        addState(sRender);
        onClose(newState);
    }

    /**
     * @param cancel The cancel button that was pressed to close the stage associated with the button
     */
    private void onClose(Button cancel) {
        ((Stage) cancel.getScene().getWindow()).close();
    }

    /**
     * Adds a state to the graph
     * @param sRender StateRender to add
     */
    private void addState(StateRender sRender) {
        myGroup.getChildren().add(sRender.getRender());
        myStateRenders.add(sRender);
    }

    /**
     * Handles user mouse dragging by moving the arrow to the appropriate position
     * @param event The new mouse position
     */
    private void dragHandle(MouseEvent event) {
        Vector vectorMousePosition = new Vector(event.getX(), event.getY());
        StateRender contained = findContainedStateRender(event);
        if(contained != null) {
            if(currentArrow == null) {
                createArrow(vectorMousePosition);
                contained.addLeavingTransition(currentArrow);
                currentArrow.setOriginal(contained);
            } else {
                currentArrow.setHead(vectorMousePosition);
                currentArrow.setDestination(contained);
            }
        }
    }

    /**
     * @param event The mouse position
     * @return The arrow that the mouse is currently in
     */
    private Arrow findContainedArrow(MouseEvent event) {
        Point2D mousePosition = new Point2D(event.getSceneX(), event.getSceneY());
        for(Arrow tRender : myArrows) {
            Node node = tRender.getRender();
            if(node.contains(mousePosition)) {
                return tRender;
            }
        }
        return null;
    }

    /**
     * @param event The mouse position
     * @return The StateRender that the mouse is currently in
     */
    private StateRender findContainedStateRender(MouseEvent event) {
        Point2D mousePosition = new Point2D(event.getSceneX(), event.getSceneY());
        for(StateRender sRender : myStateRenders) {
            Node node = sRender.getRender();
            if(node.contains(mousePosition)) {
                return sRender;
            }
        }
        return null;
    }

    /**
     * Creates an arrow at the point of the mouse
     *
     * @param vectorMousePosition The position of the mouse
     */
    private void createArrow(Vector vectorMousePosition) {
        Arrow newArrow = new Arrow(vectorMousePosition, vectorMousePosition, this);
        currentArrow = newArrow;
        myArrows.add(currentArrow);
        myGroup.getChildren().add(currentArrow.getRender());
    }

    /**
     * Handles when the arrow drag is finally closed. Removes the arrow if the original and destination state are the
     * same, otherwise it creates the arrow
     *
     * @param event End mouse position
     */
    private void dragExit(MouseEvent event) {
        StateRender contained = findContainedStateRender(event);
        if (currentArrow != null) {
            currentArrow.onClick();
            if (currentArrow.getOriginal() == contained) {
                removeMyself(currentArrow);
            }
        }
        currentArrow = null;
    }

    /**
     * Recreates all the States from the locally saved states
     */
    private void recreateStates() {
        myStates = new ArrayList<>();
        for(StateRender sRender: myStateRenders) {
            myStates.add(sRender.createNewState());
        }
    }
}

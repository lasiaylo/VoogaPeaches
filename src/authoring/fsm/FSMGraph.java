package authoring.fsm;

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
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles the rendering of the graph of a finite state machine
 * @author Albert
 * @author Simran
 */
public class FSMGraph implements GraphDelegate {
    private List<StateRender> myStateRenders;
    private List<TransitionRender> myTransitionRenders;
    private Group myGroup = new Group();
    private TransitionRender currentTRender;
    private boolean addingState;

    /**
     * Creates a new FSMGraph from scratch
     */
    public FSMGraph() {
        this(new ArrayList<StateRender>(), new ArrayList<TransitionRender>());
    }

    /**
     * Creates a new FSMGraph from existing arraylist params
     * @param stateRenders      list of stateRenders
     * @param transitionRenders list of transitionRenders
     */
    public FSMGraph(List<StateRender> stateRenders, List<TransitionRender> transitionRenders) {
        myStateRenders = stateRenders;
        myTransitionRenders = transitionRenders;
        myGroup.setOnMouseDragged(e -> dragHandle(e));
        myGroup.setOnMouseReleased(e -> dragExit(e));
        for(TransitionRender tRender: myTransitionRenders) {
            tRender.getArrow().getRender().setOnMouseDragged(e -> transitionDragHandle(e, tRender));
            tRender.getArrow().getRender().setOnMouseReleased(e -> transitionDragExit());
        }
    }

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

    private boolean validClick(MouseEvent e) {
        return !addingState && findContainedStateRender(e) == null;
    }

    private FlowPane createPopup(MouseEvent e) {
        FlowPane flow = new FlowPane();
        flow.setMinSize(100, 200);
        Button newState = new Button("Create New State?");
        Button cancel = new Button("Cancel");
        TextField name = new TextField();
        name.setPromptText("Enter your name lol");
        newState.setOnMouseClicked(f -> onCreate(e, newState, name.getText()));
        cancel.setOnMouseClicked(f -> onClose(cancel));
        flow.getChildren().addAll(newState, cancel, name);
        return flow;
    }

    private void onCreate(MouseEvent e, Button newState, String name){
        addState(new StateRender(e.getX(), e.getY(), name , new State(), this));
        onClose(newState);
    }

    private void onClose(Button cancel) {
        ((Stage) cancel.getScene().getWindow()).close();
    }

    @Override
    public void removeMyself(StateRender state) {
    }

    @Override
    public void removeMyself(Arrow arrow) {
    }

    /**
     * Adds a state to the graph
     * @param sRender   stateRender to add
     */
    private void addState(StateRender sRender) {
        myGroup.getChildren().add(sRender.getRender());
        myStateRenders.add(sRender);
    }

    /**
     * @return  the group of the FSMGraph
     */
    public Group getRender() {
        return myGroup;
    }

    /**
     * Handles user mouse dragging
     * @param event
     */
    private void dragHandle(MouseEvent event) {
        Vector vectorMousePosition = new Vector(event.getX(), event.getY());
        StateRender contained = findContainedStateRender(event);
        if(contained != null) {
            if(currentTRender == null) {
                createArrow(vectorMousePosition, contained);
                contained.addLeavingTransition(currentTRender);
            } else {
                currentTRender.setHead(vectorMousePosition);
                contained.addArrivingTransition(currentTRender);
            }
        }
    }

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

    private void transitionDragHandle(MouseEvent event, TransitionRender transition) {
        currentTRender = transition;
        currentTRender.setHead(new Vector(event.getX(), event.getY()));
    }

    private void transitionDragExit() {
        currentTRender = null;
    }

    private void createArrow(Vector vectorMousePosition, StateRender sRender) {
        Arrow newArrow = new Arrow(vectorMousePosition, vectorMousePosition, this);
        TransitionRender tRender = new TransitionRender(sRender, newArrow);
        currentTRender = tRender;
        myTransitionRenders.add(tRender);
        myGroup.getChildren().add(tRender.getArrow().getRender());
        tRender.getArrow().getRender().setOnMouseDragged(e -> transitionDragHandle(e, tRender));
        tRender.getArrow().getRender().setOnMouseReleased(e -> transitionDragExit());
    }

    private void dragExit(MouseEvent event) {
        StateRender contained = findContainedStateRender(event);
//        contained.addArrivingTransition(currentTRender);
        currentTRender = null;
    }

}

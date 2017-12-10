package authoring.fsm;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles the rendering of the graph of a finite state machine
 * @author Albert
 */
public class FSMGraph implements GraphDelegate {
    private List<StateRender> myStateRenders;
    private List<TransitionRender> myTransitionRenders;
    private Group myGroup = new Group();
    private TransitionRender currentTRender;

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

    @Override
    public void removeMyself(StateRender state) {
    }

    @Override
    public void removeMyself(TransitionRender transition) {
    }

    /**
     * Adds a state to the graph
     * @param sRender   stateRender to add
     */
    public void addState(StateRender sRender) {
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
        Arrow newArrow = new Arrow(vectorMousePosition, vectorMousePosition);
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

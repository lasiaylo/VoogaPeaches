package authoring.fsm;

import engine.fsm.Transition;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class FSMGraph {
    private List<StateRender> myStateRenders;
    private List<TransitionRender> myTransitionRenders;
    private Group myGroup;
    private TransitionRender currentTRender;
    public FSMGraph() {
        this(new ArrayList<StateRender>(), new ArrayList<TransitionRender>());
    }

    public FSMGraph(List<StateRender> stateRenders, List<TransitionRender> transitionRenders) {
        myStateRenders = stateRenders;
        myTransitionRenders = transitionRenders;
        myGroup = new Group();
        myGroup.setOnMouseDragged(e -> dragHandle(e));
        myGroup.setOnMouseReleased(e -> dragExit(e));

    }

    public void addState(StateRender sRender) {
        myGroup.getChildren().add(sRender.getRender());
        myStateRenders.add(sRender);
    }

    public Group getRender() {
        return myGroup;
    }

    private void dragHandle(MouseEvent event) {
        Point2D mousePosition = new Point2D(event.getSceneX(), event.getSceneY());
        Vector vectorMousePosition = new Vector(mousePosition.getX(), mousePosition.getY());
        if(currentTRender == null) {
            for(StateRender sRender : myStateRenders) {
                Node node = sRender.getRender();
                if(node.contains(mousePosition)) {
                    createArrow(vectorMousePosition, sRender);
                    break;
                }
            }
        } else {
            currentTRender.setHead(vectorMousePosition);
        }
    }

    private void transitionDragHandle(MouseEvent event, TransitionRender transition) {
        currentTRender = transition;
        currentTRender.setHead(new Vector(event.getSceneX(), event.getSceneY()));
    }

    private void transitionDragExit(MouseEvent event) {
        currentTRender = null;
    }

    private void createArrow(Vector vectorMousePosition, StateRender sRender) {
        Arrow newArrow = new Arrow(vectorMousePosition, vectorMousePosition);
        TransitionRender tRender = new TransitionRender(sRender, newArrow);
        currentTRender = tRender;
        myTransitionRenders.add(tRender);
        myGroup.getChildren().add(tRender.getRender().getRender());
        tRender.getRender().getRender().setOnMouseDragged((MouseEvent event) -> transitionDragHandle(event, tRender));
        tRender.getRender().getRender().setOnMouseReleased(e -> transitionDragExit(e));
    }

    private void dragExit(MouseEvent event) {
        for(StateRender sRender : myStateRenders) {
            Node node = sRender.getRender();
            if(node.contains(new Point2D(event.getSceneX(), event.getScreenY()))) {
                sRender.addArrivingTransition(currentTRender);
                break;
            }
        }
        currentTRender = null;
    }
}

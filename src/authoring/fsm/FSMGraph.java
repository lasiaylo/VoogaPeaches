package authoring.fsm;

import engine.managers.State;
import engine.managers.Transition;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
        myGroup.setOnMouseDragOver(e -> dragExit());

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
                Pane pane = sRender.getRender();
                if(pane.contains(mousePosition)) {
                    Arrow newArrow = new Arrow(vectorMousePosition, vectorMousePosition);
                    currentTRender = new TransitionRender(sRender, newArrow);
                    myTransitionRenders.add(currentTRender);
                    myGroup.getChildren().add(currentTRender.getRender().getGroup());
                    return;
                }
            }
        } else {
//            System.out.println("current not null");
            currentTRender.setHead(vectorMousePosition);
        }
    }

    private void dragExit() {
        System.out.println("done");
        currentTRender = null;
    }
}

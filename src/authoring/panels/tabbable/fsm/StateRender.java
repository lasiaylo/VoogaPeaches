package authoring.panels.tabbable.fsm;

import engine.managers.State;
import engine.managers.Transition;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class StateRender {
    private Shape myRender = new Rectangle();
    private State myState;

    private List<TransitionRender> myLeavingTransitions = new ArrayList<>();
    private List<TransitionRender> myArrivingTransitions = new ArrayList<>();

    public StateRender(double X, double Y, String title, State state) {
        myState = state;
        // color red
    }

    public void addArrivingTransition(TransitionRender tRender) {
        myArrivingTransitions.add(tRender);
    }

    public void addLeavingTransition(TransitionRender tRender) {
        myState.getTransitions().add(tRender.getTransition());
        myLeavingTransitions.add(tRender);
    }

    public void removeArrivingTransition(TransitionRender tRender) {
        myArrivingTransitions.remove(tRender);
        if(myArrivingTransitions.isEmpty()) {
            // color red
        }
    }

    public void removeLeavingTransition(TransitionRender tRender) {
        myState.getTransitions().remove(tRender.getTransition());
        myLeavingTransitions.remove(tRender);
    }

    public List<TransitionRender> getMyLeavingTransitions() {
        return myLeavingTransitions;
    }

    public List<TransitionRender> getMyArrivingTransitions() {
        return myArrivingTransitions;
    }
}

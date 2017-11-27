package authoring.fsm;

import engine.fsm.Transition;
import util.math.num.Vector;

public class TransitionRender {
    private StateRender myOrigin;
    private StateRender myDestination;
    private Transition myTransition;
    private Arrow myArrow;

    public TransitionRender(StateRender origin, Arrow arrow) {
        myOrigin = origin;
        myArrow = arrow;
        // create a new popup to set the transition
    }

    public void setTransition(Transition transition) {
        myTransition = transition;
    }

    public Arrow getArrow() {
        return myArrow;
    }

    public void setHead(Vector head) {
        myArrow.setHead(head);
    }

    public Transition getTransition() {
        return myTransition;
    }

    public void setOrigin(StateRender newOrigin) {
        myOrigin = newOrigin;
    }

    public void removeOrigin() {
        myOrigin = null;
        //color red
    }

    public void setDestination(StateRender newDestination) {
        myDestination = newDestination;
    }

    public void removeDestination() {
        myDestination = null;
        // color red
    }
}

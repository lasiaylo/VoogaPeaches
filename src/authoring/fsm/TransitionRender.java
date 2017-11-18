package authoring.fsm;

import engine.managers.Transition;

public class TransitionRender {
    private StateRender myOrigin;
    private StateRender myDestination;
    private Transition myTransition;

    public TransitionRender(StateRender origin, StateRender destination) {
        myOrigin = origin;
        myDestination = destination;
        // create a new popup to set the transition
    }

    public void setTransition(Transition transition) {
        myTransition = transition;
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

package authoring.panels.tabbable.fsm;

import engine.managers.Transition;

public class TransitionRender {
    private StateRender myOrigin;
    private StateRender myDestination;
    private Transition myTransition;

    public TransitionRender(Transition transition) {
        myTransition = transition;
    }

    public Transition getTransition() {
        return myTransition;
    }
}

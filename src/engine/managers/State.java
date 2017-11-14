package engine.managers;

import java.util.List;

public class State {
	private String myTag;
	private List<Transition> myTransitions;
	
	public State(String tag) {
		myTag = tag;
	}
	
	public void update(StateManager manager) {
		for (Transition transition : myTransitions) {
			if (transition conditions met) {
				manager.setCurrentState(transition.getDestinationState());
			}
		}
		
	}

}

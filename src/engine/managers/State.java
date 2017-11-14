package engine.managers;

import java.util.List;

public class State {
	private String myLabel;
	private List<Transition> myTransitions;
	
	/**Creates a new State with a label
	 * @param label
	 */
	public State(String label) {
		myLabel = label;
	}
	
	/**Creates a new State with no label
	 * 
	 */
	public State() {
		this("");
	}
	
	/**Checks the transitions of this state to see if the conditions are met
	 * If they are, the manager's current state will be changed to the transition's destination
	 * @param manager
	 */
	public void update(StateManager manager) {
		for (Transition transition : myTransitions) {
			if (transition.conditionsMeet(manager.getConditions())) {
				manager.setCurrentState(transition.getDestinationState());
			}
		}
	}
	
	/**
	 * @return Transitions going out of this state
	 */
	public List<Transition> getTransitions(){
		return myTransitions;
	}

}

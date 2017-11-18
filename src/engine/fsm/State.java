package engine.fsm;

import engine.managers.StateManager;

import java.util.List;

/**Represents a state within a Finite State Machine
 * 
 * @author lasia
 *
 */
public class State {
	private List<Transition> myTransitions;
	
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

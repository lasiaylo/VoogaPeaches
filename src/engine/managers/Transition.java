package engine.managers;

import java.util.Map;

public class Transition {
	private State myDestinationState;
	private Logic condition; 
	// figure out how to allow users to create their own logic out
	// of their own specified parameters
	
	/**Creates a new transition that points to a destination state
	 * @param state
	 */
	public Transition(State state) {
		myDestinationState = state;
	}
	
	/**Takes in a set of parameters and determines whether it meets the conditions specified
	 * by the user
	 * 
	 * @param Map of conditions
	 * @return boolean
	 */
	public boolean conditionsMeet(Map<String,Object> parameters) {
		
	}
	
	/**
	 * @return destination state
	 */
	public State getDestinationState() {
		return myDestinationState;
	}
}

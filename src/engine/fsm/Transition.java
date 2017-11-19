package engine.fsm;

import engine.fsm.Logic;
import engine.fsm.State;

import java.util.Map;

public class Transition {
	private State myDestinationState;
	private Logic condition;
	
	/**Creates a new transition that points to a destination state
	 * @param state
	 */
	public Transition(State state) {
		myDestinationState = state;
	}
	
	/**Takes in a set of parameters and determines whether it meets the conditions specified
	 * by the user
	 * 
	 * @param parameters 	Map of conditions
	 * @return boolean
	 */
	public boolean conditionsMeet(Map<String,Object> parameters) {
		return condition.evaluate();
	}
	
	/**
	 * @return destination state
	 */
	public State getDestinationState() {
		return myDestinationState;
	}

	/**
	 * Sets the destination state of the transition to param
	 * @param state	new state to set destination state to
	 */
	public void setDestinationState(State state) {
		myDestinationState = state;
	}
	
	/**Sets a new condition needed for this transition to move to the next state
	 * @param logicStatement
	 * @param parameter
	 */
	public void setCondition(String logicStatement, Map<String,Object> parameter) {
		condition = new Logic(logicStatement,parameter);
	}
}

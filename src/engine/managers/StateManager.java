package engine.managers;

import java.util.List;
import java.util.Map;

import engine.util.IManager;

/**Keeps track of the state of User-created finite state machine
 * State is maintained throughout the frame
 * 
 * @author lasia
 *
 */
public class StateManager implements IManager{
	private State currentState;
	private State defaultState;
	private List<State> states;
	private Map<String, Object> conditions;
	
	/** Checks whether the current state can transition to a new state
	 *  and updates current state accordingly
	 */
	public void update() {
		currentState.update(this);
	}
	
	/** Sets the current state to the user defined default state.
	 *  To be use primarily by user to debug their own state
	 */
	public void reset() {
		currentState = defaultState;
	}
	
	/**
	 * @return Map of user defined conditions
	 */
	public Map<String, Object> getConditions(){
		return conditions;
	}
	
	/**
	 * @return default state
	 */
	public State getDefaultState() {
		return defaultState;
	}

	/**Sets a new default state that the machine will always start on
	 * @param newState
	 */
	public void sertDefaultState(State newState) {
		defaultState = newState;
	}

	/**
	 * @return current state
	 */
	public State getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(State newState) {
		currentState = newState;
	}

	@Override
	
	public boolean check(Object arg1, String tag) {
		// TODO Auto-generated method stub
		return false;
	}
}

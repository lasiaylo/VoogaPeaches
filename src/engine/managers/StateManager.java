package engine.managers;

import engine.fsm.State;

import java.util.List;
import java.util.Map;

/**Keeps track of the state of User-created finite state machine
 * State is maintained throughout the frame
 * 
 * @author lasia
 *
 */
public class StateManager implements IManager{
	private State myCurrentState;
	private State myDefaultState;
	private List<State> myStates;
	private Map<String, Object> myConditions;
	
	/** Checks whether the current state can transition to a new state
	 *  and updates current state accordingly
	 */
	public void update() {
		myCurrentState.update(this);
	}
	
	/** Sets the current state to the user defined default state.
	 *  To be use primarily by user to debug their own state
	 */
	public void reset() {
		myCurrentState = myDefaultState;
	}
	
	/**
	 * @return List of states contained by this manager
	 */
	public List<State> getStates(){
		return myStates;
	}
	/**
	 * @return Map of user defined conditions
	 */
	public Map<String, Object> getConditions(){
		return myConditions;
	}
	
	/**
	 * @return default state
	 */
	public State getDefaultState() {
		return myDefaultState;
	}

	/**Sets a new default state that the machine will start on
	 * @param newState
	 */
	public void setDefaultState(State newState) {
		myDefaultState = newState;
	}

	/**
	 * @return current state
	 */
	public State getCurrentState() {
		return myCurrentState;
	}
	
	public void setCurrentState(State newState) {
		myCurrentState = newState;
	}

	@Override
	public boolean check(Object state) {
		return myCurrentState == (State) state;
	}
}

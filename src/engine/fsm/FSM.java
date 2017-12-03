package engine.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import engine.events.Event;
import engine.events.StateEvent;
import engine.fsm.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Keeps track of the state of User-created finite state machine
 * State is maintained throughout the frame
 * 
 * @author lasia
 *	@author Albert
 *	@author richardtseng
 */
public class FSM extends TrackableObject{
	@Expose private State myCurrentState;
	@Expose private State myDefaultState;
	@Expose private List<State> myStates;
	@Expose private Map<String, Object> myConditions;

	/**
	 * Creates a new StateManager from the database
	 */
	private FSM() {}

	/**
	 * Creates a new StateManager
	 * @param currentState	current State of the state manager
	 * @param defaultState	default State of the state manager
	 */
	public FSM(State currentState, State defaultState) {
		myCurrentState = currentState;
		myDefaultState = defaultState;
		myStates = new ArrayList<>();
		myConditions = new HashMap<>();
	}
	
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
	public Event getCurrentState() {
		return new StateEvent(myCurrentState.getStateName());
	}
	
	public void setCurrentState(State newState) {
		myCurrentState = newState;
	}

	public boolean check(Object state) {
		return myCurrentState == (State) state;
	}
}
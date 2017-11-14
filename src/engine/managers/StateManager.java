package engine.managers;

/**Keeps track of the state of User-created finite state machine
 * @author lasia
 *
 */
public class StateManager {
	private State currentState;
	private State defaultState;
	private State headState;
	
	public void update() {
		currentState.update();
	}
	
	public void reset() {
		currentState = defaultState;
	}
	
	public State getDefaultState() {
		return defaultState;
	}

	public void setDefaultState(State newState) {
		defaultState = newState;
	}

	public State getHeadState() {
		return headState;
	}

	public State getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(State newState) {
		currentState = newState;
	}
}

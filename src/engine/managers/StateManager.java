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
}

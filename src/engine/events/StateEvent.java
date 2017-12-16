package engine.events;

import engine.fsm.State;

/**
 * Used to publish new states from the fsm
 *
 * @author Simran
 */
public class StateEvent extends Event {

	private State state;

	public StateEvent(State currState) {
		super(EventType.STATE.getType());
		this.state = currState;
	}

	public State getState() {
		return state;
	}
}
package engine.events;

import engine.fsm.State;

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
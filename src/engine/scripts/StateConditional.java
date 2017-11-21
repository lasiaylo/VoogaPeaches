package engine.scripts;

import java.util.ArrayList;
import java.util.List;
import engine.fsm.State;
import engine.managers.StateManager;

public class StateConditional extends Conditional{
	
	public StateConditional(StateManager manager ,State state, List<IScript> scripts) {
		super(manager,state,scripts);
	}
	
	public StateConditional(StateManager manager, State state) {
		this(manager, state, new ArrayList<IScript>());
	}

}

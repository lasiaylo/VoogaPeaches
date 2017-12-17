package engine.fsm;

import engine.entities.Entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Backend for the Finite State Machine.
 *
 * @author Simran
 * @author Ramil
 */
public class FSM {

    private Map<String, State> map;
    private State current;
    private Entity entity;

    /**
     * Takes in the entity that the closures have access to. Each FSM corresponds to one entity. The config map
     * represents how we expect to read in states and their transitions. See fsm.sample.groovy for an example of the
     * config.
     *
     * @param entity Entity that the fsm is bound to
     * @param config Initial configuration of the fsm
     */
    public FSM(Entity entity, Map<String, Map<String, Map<String, Object>>> config) {
        map = new LinkedHashMap<>();
        this.entity = entity;

        for (String state : config.keySet())
            map.put(state, new State(state, config.get(state)));

        setDefault();
    }

    /**
     * Another way to read in an FSM using just a list of state objects.
     *
     * @param entity Entity that the fsm is bound to
     * @param states The list of possible states
     */
    public FSM(Entity entity, List<State> states) {
        map = new LinkedHashMap<>();
        this.entity = entity;
        for(State state: states)
            map.put(state.getName(), state);

        setDefault();
    }

    /**
     * Used to set the default state of the FSM as per the initial configuration conditions.
     */
    private void setDefault()  {
        for (State state : map.values())
            try {
                if (state.getProperty("default") != null && (Boolean) state.getProperty("default"))
                    current = state;
            } catch (ClassCastException ignored) { }
    }

    /**
     * Stepping through the FSM. Tries to transition to another state and returns the new State.
     *
     * @return Next state that the fsm is in
     */
    public State step() {
        return current = map.get(current.transition(entity));
    }
}

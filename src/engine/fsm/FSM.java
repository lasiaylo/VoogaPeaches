package engine.fsm;

import engine.entities.Entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FSM {
    private Map<String, State> map;
    private State current;
    private Entity entity;

    public FSM(Entity entity, Map<String, Map<String, Map<String, Object>>> config) {
        map = new LinkedHashMap<>();
        this.entity = entity;

        for (String state : config.keySet())
            map.put(state, new State(state, config.get(state)));

        setDefault();
    }

    public FSM(Entity entity, List<State> states) {
        map = new LinkedHashMap<>();
        this.entity = entity;
        for(State state: states)
            map.put(state.getName(), state);

        setDefault();
    }

    private void setDefault()  {
        for (State state : map.values())
            try {
                if (state.getProperty("default") != null && (Boolean) state.getProperty("default"))
                    current = state;
            } catch (ClassCastException ignored) {
            }
        if (current == null)
            // TODO throw error
            System.out.println("No default state specified");
    }

    public State step() {
        return current = map.get(current.transition(entity));
    }
}

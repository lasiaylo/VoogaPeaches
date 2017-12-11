package engine.fsm;

import engine.entities.Entity;

import java.util.LinkedHashMap;

public class FSM {
    private LinkedHashMap<String, State> map;
    private State current;
    private Entity entity;

    public FSM(Entity entity, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> config) {
        map = new LinkedHashMap<>();
        this.entity = entity;

        for (String state : config.keySet())
            map.put(state, new State(state, config.get(state)));

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

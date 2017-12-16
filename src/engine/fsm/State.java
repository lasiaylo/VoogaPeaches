package engine.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import engine.entities.Entity;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents individual states in an FSM. These classes store information about different properties in the state
 * that are stored in the form of a map where key is the name of the variable and the associated Object value is
 * expected to be a boolean, string, or double. Transitions store information about their related states the closure
 * code associated with them
 *
 * @author Ramil
 * @author Simran
 */
public class State extends TrackableObject {

    @Expose private String name;
    @Expose private Map<String, String> code;
    @Expose private Map<String, Object> properties;
    private Map<String, Closure> transitions;
    private GroovyShell shell;

    /**
     * Creates all the private instance variables from the data of a state.
     *
     * @param name Name of state
     * @param data Information about state
     */
    public State(String name, Map<String, Map<String, Object>> data) {
        this.name = name;
        if (!data.containsKey("transitions")) { return; }
        transitions = new LinkedHashMap<>();
        code = new LinkedHashMap<>();
        shell = new GroovyShell();
        for (Map.Entry<String, Object> entry : data.get("transitions").entrySet())
            updateCode(entry.getKey(), (String) entry.getValue());
        this.properties = data.get("properties");
    }

    private State() { }

    /**
     * Returns value of property with a certain name. Primarily used to check for defaults.
     *
     * @param name Name of desired property
     * @return Value associated with a name
     */
    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    /**
     * Get the name of the state. Needed to create a map of multiple states.
     *
     * @return Returns the name of the state
     */
    public String getName() {
        return name;
    }

    /**
     * Convert string version of transitions to executable Groovy closures
     *
     * @param name Name of next state on a transition
     * @param code String put into the transitions
     */
    private void updateCode(String name, String code) {
        transitions.put(name, (Closure) shell.evaluate(code));
        this.code.put(name, code);
    }

    /**
     * Runs the transition to return the next state with an entity as an input, if the transition returns true
     *
     * @param entity Entity that the transitions have access to
     * @return Next state name
     */
    String transition(Entity entity) {
        String name = null;
        for (Map.Entry<String, Closure> entry : transitions.entrySet())
            name = (Boolean) entry.getValue().call(entity, this) ? entry.getKey() : name;
        return name;
    }

    @Override
    public void initialize() { }
}

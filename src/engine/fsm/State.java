package engine.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import engine.entities.Entity;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class State extends TrackableObject {
    @Expose private String name;
    private Map<String, Closure> transitions;
    @Expose private Map<String, String> code;
    @Expose private Map<String, Object> properties;
    private GroovyShell shell;

    private State() { }

    public State(String name, Map<String, Map<String, Object>> data) {
        this.name = name;

        if (!data.containsKey("transitions"))
            return;

        transitions = new LinkedHashMap<>();
        code = new LinkedHashMap<>();
        shell = new GroovyShell();

        for (Map.Entry<String, Object> entry : data.get("transitions").entrySet())
            updateCode(entry.getKey(), (String) entry.getValue());

        this.properties = data.get("properties");
    }

    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    public String getName() {
        return name;
    }

    public void updateCode(String name, String code) {
        transitions.put(name, (Closure) shell.evaluate(code));
        this.code.put(name, code);
    }

    public Iterator<Map.Entry<String, String>> getTransitions() {
        return code.entrySet().iterator();
    }

    String transition(Entity entity) {
        String name = null;
        for (Map.Entry<String, Closure> entry : transitions.entrySet())
            name = (Boolean) entry.getValue().call(entity, this) ? entry.getKey() : name;
        return name;
    }

    @Override
    public void initialize() {
    }
}

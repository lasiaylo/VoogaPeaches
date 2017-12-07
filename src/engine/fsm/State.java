package engine.fsm;

import groovy.lang.Closure;
import groovy.lang.GroovyShell;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class State {
    private String name;
    private LinkedHashMap<String, Closure> transitions;
    private LinkedHashMap<String, String> code;
    private LinkedHashMap<String, Object> properties;
    private GroovyShell shell;

    State(String name, LinkedHashMap<String, LinkedHashMap<String, Object>> data) {
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
        System.out.println(name);
        System.out.println(code);
        transitions.put(name, (Closure) shell.evaluate(code));
        this.code.put(name, code);
    }

    public Iterator<Map.Entry<String, String>> getTransitions() {
        return code.entrySet().iterator();
    }

    String transition() {
        String name = null;
        for (Map.Entry<String, Closure> entry : transitions.entrySet())
            name = (Boolean) entry.getValue().call() ? entry.getKey() : name;
        return name;
    }
}

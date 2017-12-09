package engine.entities;

import database.fileloaders.ScriptLoader;
import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EntityScriptFactory {

    static void executeScripts(Entity entity) {
        Map<String, Object> properties = entity.getProperties();
        parseScripts(entity, properties);
        parseListeners(entity, properties);
    }

    private static void parseScripts(Entity entity, Map<String, Object> properties) {
        List scripts = (List) properties.getOrDefault("scripts", new HashMap<String, ArrayList<Map>>());

        for (Object o : scripts) {
            Map<String, Object> bindings = new HashMap<>();
            parse(entity, bindings, o).call(bindings);
        }
    }

    private static void parseListeners(Entity entity, Map<String, Object> properties) {
        Map listeners = (Map) properties.getOrDefault("listeners", new HashMap<String, ArrayList<Map>>());

        for (Object o : listeners.entrySet()) {
            String type = (String) ((Map.Entry) o).getValue();

            List callbacks = (List) properties.getOrDefault(type, new HashMap<String, ArrayList<Map>>());

            for (Object oo : callbacks) {
                Map<String, Object> bindings = new HashMap<>();
                Closure callback = parse(entity, bindings, o);

                entity.on(type, (event) -> {
                    bindings.put("event", event);
                    callback.call(bindings);
                });
            }
        }
    }

    private static Closure parse(Entity entity, Map<String, Object> bindings, Object o) {
        String name;

        try {
            name = (String) o;
        } catch (ClassCastException e) {
            Map params = (Map) o;

            name = (String) params.get("name");
            addBindings(entity, params, bindings);
        }

        return ScriptLoader.stringForFile(name);
    }

    private static void addBindings(Entity entity, Map params, Map<String, Object> bindings) {
        bindings.put("entity", entity);
        bindings.put("game", entity.getRoot());

        if (params != null)
            for (Object e : params.entrySet())
                bindings.put((String) ((Map.Entry) e).getKey(), ((Map.Entry) e).getValue());
    }
}

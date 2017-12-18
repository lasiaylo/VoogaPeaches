package engine.entities;

import database.fileloaders.ScriptLoader;
import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that executes all scripts from a passed in Entity and
 * caches the associated callbacks
 * @author ramilmsh
 * @author Albert
 */
class  EntityScriptFactory {

    /**
     * Executes and caches all scripts and listeners contained by an entity
     * @param entity    Entity to execute scripts/listeners of
     */
    static void executeScripts(Entity entity) {
        Map<String, Object> properties = entity.getProperties();
        parseScripts(entity, properties);
        parseListeners(entity, properties);
    }

    /**
     * Parses initial scripts that should be reexecuted only on runtime/substitution. Executes these scripts
     * @param entity        Entity to execute scripts of
     * @param properties    properties map of @param entity
     */
    private static void parseScripts(Entity entity, Map<String, Object> properties) {
        Map scripts = (Map) properties.getOrDefault("scripts", new HashMap<String, ArrayList<Map>>());
        for (Object o : scripts.entrySet()) {
            Map<String, Object> bindings = new HashMap<>();
            parse(entity, bindings, (Map.Entry) o).call(entity, bindings);
        }
    }

    /**
     * Parses listeners and its associated scripts that must be continually reexecuted. Cache these as closures inside
     * Evented to be executed on listen object
     * @param entity        Entity to parse listeners over
     * @param properties    properties map of @param entity
     */
    private static void parseListeners(Entity entity, Map<String, Object> properties) {
        Map listeners = (Map) properties.getOrDefault("listeners", new HashMap<String, ArrayList<Map>>());
        for (Object o : listeners.entrySet()) {
            String type = (String) ((Map.Entry) o).getKey();
            Map callbacks = (Map) listeners.getOrDefault(type, new HashMap<>());
            for (Object oo : callbacks.entrySet()) {
                Map<String, Object> bindings = new HashMap<>();
                Closure callback = parse(entity, bindings, (Map.Entry) oo);
                entity.on(type, (event) -> callback.call(entity, bindings, event));
            }
        }
    }

    /**
     * Loads in a script by name from the database
     * @param entity    Entity script is attached to
     * @param bindings  Parameters passed into script
     * @param entry     Map entry that represents a binding to be added to bindings map
     * @return          Script as closure from database
     */
    private static Closure parse(Entity entity, Map<String, Object> bindings, Map.Entry entry) {
        String name = (String) entry.getKey();
        Map params = (Map) entry.getValue();
        addBindings(entity, params, bindings);
        return ScriptLoader.getScript(name);
    }

    /**
     * Adds bindings (parameters) to a script
     * @param entity    Entity to whom this script is attached
     * @param params    Map of params to be added to bindings
     * @param bindings  bindings to whom params should be added
     */
    private static void addBindings(Entity entity, Map params, Map<String, Object> bindings) {
        if (params != null)
            for (Object e : params.entrySet())
                bindings.put((String) ((Map.Entry) e).getKey(), ((Map.Entry) e).getValue());
    }
}

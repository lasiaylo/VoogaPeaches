package engine.entities;

import database.fileloaders.ScriptLoader;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EntityScriptFactory {

    public static void executeScripts(Entity entity) {
        Map<String, Object> properties = entity.getProperties();
        Map<String, Map<String, Object>> scriptMap = (Map<String, Map<String, Object>>)
                properties.getOrDefault("scripts", new HashMap<String, Map<String, Object>>());
        for (String script : scriptMap.keySet() ) {
            String code = ScriptLoader.stringForFile(script);

            System.out.println(scriptMap.get(script).get("actions").getClass());
            List<String> listenActionPair = (List<String>) scriptMap.get(script).get("actions");
            Map<String, Object> params = (Map<String, Object>) scriptMap.get(script).get("bindings");

            Binding binding = new Binding();
            binding.setVariable("entity", entity);
            binding.setVariable("game", entity.getRoot());
            binding.setVariable("actions", listenActionPair);
            Iterator<String> paramIter = params.keySet().iterator();
            paramIter.forEachRemaining(key -> {
                String whiteSpaceReplace = key.replaceAll("\\s+", "_").toLowerCase();
                if(!key.equals(whiteSpaceReplace)) {
                    params.put(whiteSpaceReplace, params.get(key));
                    params.remove(key);
                    key = whiteSpaceReplace;
                }
                binding.setVariable(key, params.get(key));
            });
            new GroovyShell(binding).evaluate(code);
        }
    }
}

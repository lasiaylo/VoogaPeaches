package default_scripts

import database.fileloaders.ScriptLoader
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType

{ Entity entity, Map<String, Object> bindings, Event event = null ->

    actions = (List<String>) actions

    entity.on(EventType.COLLISION, { Event e ->
        e = (engine.events.TickEvent) e
        Binding binding = new Binding()
        binding.setVariable("event", e)
        binding.setVariable("entity", entity)
        GroovyShell shell = new GroovyShell(binding)
        for (String action : actions)
            shell.evaluate(ScriptLoader.getScript(action))
    })
}
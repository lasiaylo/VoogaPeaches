package scriptdata

import database.fileloaders.ScriptLoader
import engine.events.Event
import engine.events.EventType

actions = (List<String>) actions

entity.on(EventType.ACCELERATE, { Event event ->
    event = (engine.events.AccelerateEvent) event

    Binding binding = new Binding()
    binding.setVariable("event", event)
    binding.setVariable("entity", entity)
    GroovyShell shell = new GroovyShell(binding)

    for (String action : actions)
        shell.evaluate(ScriptLoader.stringForFile(action))
})
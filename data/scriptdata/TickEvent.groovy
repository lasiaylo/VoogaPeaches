package scriptdata

import database.fileloaders.ScriptLoader
import engine.events.Event
import engine.events.EventType

actions = (List<String>) actions

entity.on(EventType.TICK, { Event event ->
    event = (engine.events.TickEvent) event

    for(String action : actions) {
        String code = ScriptLoader.stringForFile(action)
        evaluate(code)
    }
})
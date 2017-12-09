package scriptdata

import database.fileloaders.ScriptLoader
import engine.events.Event
import engine.events.EventType

actions = (List<String>) actions

entity.on(EventType.MOVE, { Event event ->
    event = (engine.events.MoveEvent) event

    for(String action : actions) {
        String code = ScriptLoader.stringForFile(action)
        evaluate(code)
    }
})
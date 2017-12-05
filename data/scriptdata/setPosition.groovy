import engine.entities.Entity
import engine.events.Event
import engine.events.MoveEvent

entity = (Entity) entity;

entity.on("move", { Event event ->
    MoveEvent moveEvent = (MoveEvent) event

    entity.setProperty("x", new Double(entity.getProperty("x").doubleValue() + moveEvent.getDx()))
    entity.setProperty("y", new Double(entity.getProperty("y").doubleValue() + moveEvent.getDy()))

    println entity.getProperty("x")
    println entity.getProperty("y")
    entity.getNodes().relocate(entity.getProperty("x"), entity.getProperty("y"))
})


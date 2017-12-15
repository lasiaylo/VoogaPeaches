package scripts

import engine.collisions.HitBox
import engine.entities.Entity
import engine.events.Event
import engine.events.EventType
import engine.events.MoveEvent

entity = (Entity) entity;

entity.on(EventType.MOVE.getType(), { Event event ->
    MoveEvent moveEvent = (MoveEvent) event
    println(new Double(entity.getProperty("x").doubleValue() + moveEvent.getDx()))
    println(new Double(entity.getProperty("y").doubleValue() + moveEvent.getDy()))
    if(new Double(entity.getProperty("x").doubleValue() + moveEvent.getDx()) > 0)
        entity.setProperty("x", new Double(entity.getProperty("x").doubleValue() + moveEvent.getDx() ))
    if(new Double(entity.getProperty("y").doubleValue() + moveEvent.getDy()) > 0)
        entity.setProperty("y", new Double(entity.getProperty("y").doubleValue() + moveEvent.getDy() ))
    entity.getNodes().relocate(entity.getProperty("x"), entity.getProperty("y"))
    for(HitBox hitBox : entity.getHitBoxes()) {
        hitBox.moveHitBox(((Number) entity.getProperty("x")).doubleValue(), ((Number) entity.getProperty("y")).doubleValue())
    }
})
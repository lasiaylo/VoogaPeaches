package default_scripts

import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import engine.entities.Entity
import engine.events.Event
import engine.events.ImageViewEvent
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode
import util.math.num.Vector

{ Entity entity, Map<String, Object> bindings, Event event ->
    event = (KeyPressEvent) event

    switch (event.keyCode) {
        case [KeyCode.W, KeyCode.UP]:
            entity.setProperty("vy", - bindings.get("velocity"))
            entity.setProperty("vx", 0)
            break

        case [KeyCode.A, KeyCode.LEFT]:
            entity.setProperty("vx", - bindings.get("velocity"))
            entity.setProperty("vy", 0)
            break

        case [KeyCode.S, KeyCode.DOWN]:
            entity.setProperty("vy", bindings.get("velocity"))
            entity.setProperty("vx", 0)
            break

        case [KeyCode.D, KeyCode.RIGHT]:
            entity.setProperty("vx", bindings.get("velocity"))
            entity.setProperty("vy", 0)
            break

        case [KeyCode.SPACE]:
            entity.setProperty("vx", 0)
            entity.setProperty("vy", 0)
            break

        case [KeyCode.DIGIT1]:
            ObjectFactory p = new ObjectFactory("Blank.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity j = p.newObject()
            entity.parent.add(j)
            ObjectFactory o = new ObjectFactory("Fact1.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity i = o.newObject()
            entity.parent.add(i)
            entity.setProperty("vx", 0)
            entity.setProperty("vy", 0)
            break

        case [KeyCode.DIGIT2]:
            ObjectFactory p = new ObjectFactory("Blank.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity j = p.newObject()
            entity.parent.add(j)
            ObjectFactory o = new ObjectFactory("Fact2.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity i = o.newObject()
            entity.parent.add(i)
            entity.setProperty("vx", 0)
            entity.setProperty("vy", 0)
            break

        case [KeyCode.DIGIT3]:
            ObjectFactory p = new ObjectFactory("Blank.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity j = p.newObject()
            entity.parent.add(j)
            ObjectFactory o = new ObjectFactory("Fact3.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity i = o.newObject()
            entity.parent.add(i)
            entity.setProperty("vx", 0)
            entity.setProperty("vy", 0)
            break

        case [KeyCode.DIGIT4]:
            ObjectFactory p = new ObjectFactory("Blank.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity j = p.newObject()
            entity.parent.add(j)
            ObjectFactory o = new ObjectFactory("Fact4.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity i = o.newObject()
            entity.parent.add(i)
            entity.setProperty("vx", 0)
            entity.setProperty("vy", 0)
            break

        case [KeyCode.DIGIT5]:
            ObjectFactory p = new ObjectFactory("Blank.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity j = p.newObject()
            entity.parent.add(j)
            ObjectFactory o = new ObjectFactory("Fact5.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity i = o.newObject()
            entity.parent.add(i)
            entity.setProperty("vx", 0)
            entity.setProperty("vy", 0)
            break

        case [KeyCode.DIGIT0]:
            ObjectFactory o = new ObjectFactory("Blank.json",JSONDataFolders.ENTITY_BLUEPRINT)
            Entity i = o.newObject()
            entity.parent.add(i)
            entity.setProperty("vx", 0)
            entity.setProperty("vy", 0)
            break
    }
}

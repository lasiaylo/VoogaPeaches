package filedata.scripts

import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import engine.entities.Entity
import engine.events.Event
import engine.events.KeyPressEvent
import javafx.scene.input.KeyCode

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    Random r = new Random()
    int time = Math.abs(r.nextInt())
    if (time % 5 == 0)
        ObjectFactory o = new ObjectFactory("Coin.json", JSONDataFolders.ENTITY_BLUEPRINT)
        Entity i = o.newObject()
        entity.parent.add(i)
        Random x = new Random()
        double xPos = Math.abs(x.nextInt()) % 250
        i.setProperty("x", xPos)
        Random y = new Random()
        double yPos = Math.abs(y.nextInt()) % 250
        i.setProperty("y", yPos)
}
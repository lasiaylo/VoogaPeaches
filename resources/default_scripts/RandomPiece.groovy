package default_scripts

import database.ObjectFactory
import database.jsonhelpers.JSONDataFolders
import engine.entities.Entity
import engine.events.Event
import util.math.num.Vector

{ Entity entity, Map<String, Object> bindings, Event event = null ->
    String[] t = ["BlueI.json", "BlueL.json",
                    "GreenZ.json", "OrangeL.json",
                    "PurpleT.json", "RedZ.json",
                    "YellowO.json"];
    Random r = new Random();
    int x = Math.abs(r.nextInt()) % 7;
    ObjectFactory o = new ObjectFactory(t[x], JSONDataFolders.ENTITY_BLUEPRINT);
    Entity i = o.newObject();
    entity.parent.add(i);
}
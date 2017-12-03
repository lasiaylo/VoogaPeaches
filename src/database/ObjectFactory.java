package database;

import database.firebase.TrackableObject;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import org.json.JSONObject;
import util.exceptions.ObjectBlueprintNotFoundException;

import java.util.Map;

public class ObjectFactory {

    private JSONObject blueprintJSON;
    private JSONToObjectConverter<Entity> converter;

    public ObjectFactory(String objectName) throws ObjectBlueprintNotFoundException {
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        blueprintJSON = manager.readJSONFile(objectName);
        if(blueprintJSON == null) throw new ObjectBlueprintNotFoundException();
        converter = new JSONToObjectConverter<>(Entity.class);
    }

    public Entity newObject() {
        return converter.createObjectFromJSON(Entity.class,blueprintJSON);
    }

    public Entity newObject(Map<String, Object> overriden) {
        JSONObject modifiedBlueprint = new JSONObject();
        for(String key : blueprintJSON.keySet()) {
            if(overriden.containsKey(key)){
                modifiedBlueprint.put(key, overriden.get(key));
            } else {
                modifiedBlueprint.put(key, blueprintJSON.get(key));
            }
        }
        return converter.createObjectFromJSON(Entity.class, modifiedBlueprint);

    }
}

package database;

import database.firebase.TrackableObject;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import org.json.JSONObject;
import util.exceptions.ObjectBlueprintNotFoundException;

import javax.sound.midi.Track;
import java.util.Map;

public class ObjectFactory<T extends TrackableObject> {

    private JSONObject blueprintJSON;
    private Class<T> objectType;
    private JSONToObjectConverter<T> converter;

    public ObjectFactory(String objectName, Class<T> objectType) throws ObjectBlueprintNotFoundException {
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        blueprintJSON = manager.readJSONFile(objectName);
        if(blueprintJSON == null) throw new ObjectBlueprintNotFoundException();
        this.objectType = objectType;
        converter = new JSONToObjectConverter<>(objectType);
    }

    public T newObject() {
        return converter.createObjectFromJSON(objectType,blueprintJSON);
    }

    public T newObject(Map<String, Object> overriddenParams) {
        JSONObject modifiedBlueprint = new JSONObject();
        for(String key : blueprintJSON.keySet()) {
            if(overriddenParams.containsKey(key)){
                modifiedBlueprint.put(key, overriddenParams.get(key));
            } else {
                modifiedBlueprint.put(key, blueprintJSON.get(key));
            }
        }
        return converter.createObjectFromJSON(objectType, modifiedBlueprint);
    }
}

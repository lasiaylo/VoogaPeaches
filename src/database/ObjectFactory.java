package database;

import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import org.json.JSONObject;
import util.exceptions.ObjectBlueprintNotFoundException;

import java.util.Map;

/**
 * A class for creating entities from a given blueprint, so that entities can
 * create entities for things like attacks 
 */
public class ObjectFactory {

    /* Instance Variables */
    private JSONObject blueprintJSON;
    private JSONToObjectConverter<Entity> converter;

    /**
     * Creates a new factory capable of creating objects from the blueprint
     * stored in the database
     * @param objectName is a {@code String} corresponding to the name of the
     *                   type of Entity that needs to be created
     * @throws ObjectBlueprintNotFoundException if the object's blueprint is
     * not found within the database
     */
    public ObjectFactory(String objectName) throws ObjectBlueprintNotFoundException {
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.ENTITY_BLUEPRINT);
        blueprintJSON = manager.readJSONFile(objectName);
        if(blueprintJSON == null) throw new ObjectBlueprintNotFoundException();
        converter = new JSONToObjectConverter(Entity.class);
    }

    /**
     * @return A new {@code Entity} corresponding to the blueprint for this object
     */
    public Entity newObject() {
        return converter.createObjectFromJSON(Entity.class,blueprintJSON);
    }

    /**
     * Creates a new object from the blueprint, but overrides the JSON blueprint
     * that is currently held
     * @param overriddenParams is a {@code Map<String, Object>} that holds the properties
     *                         to override within the JSON object
     * @return A new {@code Entity} corresponding to the blueprint for this object but with
     * the overriden params set
     */
    public Entity newObject(Map<String, Object> overriddenParams) {
        JSONObject modifiedBlueprint = new JSONObject();
        for(String key : blueprintJSON.keySet()) {
            if(overriddenParams.containsKey(key)){
                modifiedBlueprint.put(key, overriddenParams.get(key));
            } else {
                modifiedBlueprint.put(key, blueprintJSON.get(key));
            }
        }
        return converter.createObjectFromJSON(Entity.class, modifiedBlueprint);
    }
}

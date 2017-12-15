package database;

import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONToObjectConverter;
import engine.entities.Entity;
import org.json.JSONObject;
import util.PropertiesReader;
import util.exceptions.ObjectBlueprintNotFoundException;

import java.io.File;
import java.util.Map;

/**
 * A class for creating entities from a given blueprint, so that entities can
 * create entities for things like attacks
 *
 * @author Walker Willetts
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
     */
    public ObjectFactory(String objectName, JSONDataFolders folder) {
        setObjectBlueprint(objectName, folder);
    }

    /**
     * Sets the ObjectFactory to use a new JSON file's blueprint
     * @param objectName is {@code String} representing the name of the file corresponding
     *                   to the blueprint you want to use
     * @throws ObjectBlueprintNotFoundException if the blueprint for the objectName is not
     * found within the database
     */
    public void setObjectBlueprint(String objectName, JSONDataFolders folder) {
        JSONDataManager manager = new JSONDataManager(folder);
        blueprintJSON = manager.readJSONFile(objectName);
        converter = new JSONToObjectConverter(Entity.class);
        System.out.println(blueprintJSON.toString(4));
    }

    /**
     * @return A new {@code Entity} corresponding to the blueprint for this object
     */
    public Entity newObject() {
        return converter.createObjectFromJSON(Entity.class, blueprintJSON);
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
        System.out.println(modifiedBlueprint.toString(4));
        return converter.createObjectFromJSON(Entity.class, modifiedBlueprint);
    }

    /**
     * @return A {@code String[]} of all the valid Entity Blueprint Names
     */
    public static String[] getEntityTypes(JSONDataFolders folder) {
        if(folder == JSONDataFolders.DEFAULT_USER_ENTITY)
            return new File(PropertiesReader.path("default_blueprints")).list();
        if(folder == JSONDataFolders.ENTITY_BLUEPRINT)
            return new File(PropertiesReader.path("blueprints")).list();
        return new String[0];
    }

}

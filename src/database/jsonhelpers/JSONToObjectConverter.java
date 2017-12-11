package database.jsonhelpers;

import database.User;
import database.firebase.TrackableObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.sound.midi.Track;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used for the conversion of JSON files into their appropriate TrackableObject
 * representations
 * @param <T> is the class of TrackableObject to be created from the JSON file
 *
 * @author Walker Willetts
 */
public class JSONToObjectConverter<T extends TrackableObject> {

    /* Instance Variables */
    private Class<T> myClass;

    /**
     * Creates a new JSONToObjectConverter that is capable of creating objects
     * of the passed Class
     * @param className is a {@code Class<T>} representing the class of objects to
     *                  be created by the converter
     */
    public JSONToObjectConverter(Class<T> className) {
        myClass = className;
    }

    /**
     * Converts the JSONObject into a map reflecting the parameters of the
     * object being created from the JSON file
     * @param json is a {@code JSONObject} that represents the JSON
     *            serialization of the object to be created by the converter
     * @return A {@code Map<String, Object>} containing the
     * Instance Variable -> Value pairs
     */
    private Map<String, Object> parseParameters(JSONObject json) {
        Map<String, Object> params = new HashMap<>();
        for(String key : json.keySet()) {
            params.put(key, json.get(key));
            if(json.get(key).getClass() == JSONObject.class) {
                // Create map for params of object that is being held by the overall object
                params.put(key, parseParameters((JSONObject) json.get(key)));
            } else if(json.get(key).getClass() == JSONArray.class) {
                params.put(key, ((JSONArray) json.get(key)).toList());
            }
        }
        return params;
    }

    /**
     * Creates a new object instance for the class provided
     * @param myClass is the {@code Class<G>} to be created
     * @return A new {@code G} instance that DOES NOT HAVE any
     * instance variables initialized
     */
    private <G extends TrackableObject> G createNewInstance(Class<G> myClass) {
        try {
            G newObject;
            // Retrieve the empty constructor
            Constructor<G> constructor = myClass.getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                // If private, then make public in order to create new instance
                constructor.setAccessible(true);
                newObject = constructor.newInstance();
                constructor.setAccessible(false);
            } else {
                newObject = constructor.newInstance();
            }
            return newObject;
        } catch(Exception e) {
            return null;
        }
    }

    private <G extends TrackableObject> void setUIDField(Class<G> myClass, Map<String, Object> params, G newObject) {
        try {
            Class<?> trackableClass = newObject.getClass().getSuperclass();
            while (trackableClass != TrackableObject.class)
                trackableClass = trackableClass.getSuperclass();
            Field UIDField = trackableClass.getDeclaredField("UID");
            UIDField.setAccessible(true);
            if(params.containsKey("UID")) {
                UIDField.set(newObject, params.get("UID"));
            } else {
                UIDField.set(newObject, newObject.UIDforObject());
            }
            UIDField.setAccessible(false);
            params.remove("UID");
        } catch (Exception e) {
            return;
        }
    }

    private <G extends TrackableObject> void instantiateParmeter(Field instanceVar, String param, Map<String,Object> params) {
        // First need to check special case where you're storing a List<? extends TrackableObject> variable
        if(List.class.isAssignableFrom(instanceVar.getType())) {
            // Get generic parameter of list
            Class listType  = (Class<?>) ((ParameterizedType) instanceVar.getGenericType()).getActualTypeArguments()[0];
            // Check to see if the list type extends TrackableObject
            if(TrackableObject.class.isAssignableFrom(listType)){
                // Case where List<? extends TrackableObject>
                List<HashMap<String,Object>> trackableObjects = (List<HashMap<String,Object>>) params.get(param);
                // Create new List<Objects> for each object
                List<Object> objectsList = new ArrayList<>();
                for(HashMap<String,Object> obj : trackableObjects) {
                    JSONObject heldObjectJSON = new JSONObject((HashMap<String, Object>) obj);
                    JSONObject m = new JSONObject(parseParameters(heldObjectJSON));
                    TrackableObject heldObject = (TrackableObject) createObjectFromJSON(listType, m);
                    heldObject.initialize();
                    objectsList.add(heldObject);
                }
                params.put(param, objectsList);
            }
            // Next need to check if the parameter is actually just a TrackableObject
        } else if(TrackableObject.class.isAssignableFrom(instanceVar.getType())) {
            JSONObject heldObjectJSON = new JSONObject((HashMap<String, Object>) params.get(param));
            Object heldObject = createObjectFromJSON((Class<G>)instanceVar.getType(), heldObjectJSON);
            params.put(param, heldObject);
            // Finally check if the parameter is a Map
        } else if(instanceVar.getType().isAssignableFrom(Map.class)) {
            JSONObject objectForMap = new JSONObject((HashMap<String, Object>) params.get(param));
            params.put(param, parseParameters(objectForMap));
        }
    }

    private <G extends TrackableObject> void setInstanceVariable(Field instanceVar, G newObject, Map<String, Object> params, String param) {
        try {
            if(!instanceVar.isAccessible()) {
                instanceVar.setAccessible(true);
                instanceVar.set(newObject, params.get(param));
                instanceVar.setAccessible(false);
                return;
            }
            instanceVar.set(newObject, params.get(param));
        } catch (Exception e) {
            // Do Nothing
        }
    }

    /**
     * Creates the appropriate initialized object corresponding to the passed in JSONObject
     * @param myClass is the {@Class} of the object being returned
     * @param json is the {@code JSONObject} holding all the object instance variable values
     * @param <G> is the type that is to be created
     * @return A new {@code G} object that has been initialized using the values in the JSONObject
     */
    public <G extends TrackableObject> G createObjectFromJSON(Class<G> myClass, JSONObject json) {
        Map<String, Object> params = parseParameters(json);
        try {
            // Create a new instance of the object class
            G newObject = createNewInstance(myClass);
            // Set UID field of TrackableObject
            if(TrackableObject.class.isAssignableFrom(myClass)) setUIDField(myClass,params, newObject);
            // Set the instance variables of the object being created
            for(String param : params.keySet()) {
                // First get the instance variable
                Field instanceVar = newObject.getClass().getDeclaredField(param);
                // Then Instantiate properly the corresponding parameter if it is an object
                instantiateParmeter(instanceVar, param, params);
                // Set the instance variable in the newly created object
                setInstanceVariable(instanceVar, newObject, params, param);
            }
            // Call class defined extra initialization
            newObject.initialize();
            // Add object to tracking map
            TrackableObject.trackTrackableObject(newObject);
            return newObject;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}

package database.jsonhelpers;

import database.firebase.TrackableObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.sound.midi.Track;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONToObjectConverter<T extends TrackableObject> {

    private Class<T> myClass;
    private JSONDataManager manager;

    public JSONToObjectConverter(Class<T> className) {
        myClass = className;
        manager = new JSONDataManager(JSONDataFolders.IMAGES);
    }

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


    public <G extends TrackableObject> G createObjectFromJSON(Class<G> myClass, JSONObject json) {
        Map<String, Object> params = parseParameters(json);
        try {
            // Get constructor and create new instance of object
            Constructor<G> constructor = myClass.getDeclaredConstructor();
            G newObject;
            if(!constructor.isAccessible()) {
                constructor.setAccessible(true);
                newObject = constructor.newInstance();
                constructor.setAccessible(false);
            } else {
                newObject = constructor.newInstance();
            }

            // Set UID field of TrackableObject
            if(TrackableObject.class.isAssignableFrom(myClass)) {
                Class<?> trackableClass = newObject.getClass().getSuperclass();
                while(trackableClass != TrackableObject.class)
                    trackableClass = trackableClass.getSuperclass();
                Field UIDField = trackableClass.getDeclaredField("UID");
                UIDField.setAccessible(true);
                UIDField.set(newObject, params.get("UID"));
                UIDField.setAccessible(false);
                params.remove("UID");
            }

            // Set the instance variables of the object being created
            for(String param : params.keySet()) {
                //if(param.equals("UID") && !TrackableObject.class.isAssignableFrom(newObject.getClass())) continue;
                // First get the instance variable
                Field instanceVar = newObject.getClass().getDeclaredField(param);
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

                if(!instanceVar.isAccessible()) {
                    instanceVar.setAccessible(true);
                    instanceVar.set(newObject, params.get(param));
                    instanceVar.setAccessible(false);
                    continue;
                }
                instanceVar.set(newObject, params.get(param));
            }
            newObject.initialize();
            TrackableObject.trackTrackableObject(newObject);
            return newObject;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}

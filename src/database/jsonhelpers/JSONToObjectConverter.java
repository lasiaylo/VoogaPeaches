package database.jsonhelpers;

import database.examples.realtime.Post;
import database.firebase.TrackableObject;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
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
            if(json.get(key) instanceof Number) {
                // Convert Number object to appropriate type for object T
                Number convertedVal = convertValue(key, (Long) json.get(key));
                params.put(key, convertedVal);
            } else if(json.get(key).getClass() == JSONObject.class) {
                // Create map for params of object that is being held by the overall object
                params.put(key, parseParameters((JSONObject) json.get(key)));
            }
        }
        return params;
    }

    private Number convertValue(String param, Number value) {
        try {
            Class<?> fieldType = myClass.getDeclaredField(param).getType();
            if(fieldType == int.class) return new Integer(value.intValue());
            if(fieldType == double.class) return new Double(value.doubleValue());
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <G> G createObjectFromJSON(Class<G> myClass, JSONObject json) {
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
            Field UIDField = newObject.getClass().getSuperclass().getDeclaredField("UID");
            UIDField.setAccessible(true);
            UIDField.set(newObject, params.get("UID"));
            UIDField.setAccessible(false);
            params.remove("UID");

            // Set the instance variables of the object being created
            for(String param : params.keySet()) {
                Field instanceVar = newObject.getClass().getDeclaredField(param);

                // Recursively create objects that are being held by the original object
                if(TrackableObject.class.isAssignableFrom(instanceVar.getType())) {
                    Object heldObject = (Object) createObjectFromJSON(instanceVar.getType(), (JSONObject) params.get(param));
                    params.put(param, heldObject);
                }

                if(!instanceVar.isAccessible()) {
                    instanceVar.setAccessible(true);
                    instanceVar.set(newObject, params.get(param));
                    instanceVar.setAccessible(false);
                    continue;
                }
                instanceVar.set(newObject, params.get(param));
            }
            return newObject;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args){
        Post p = new Post("test","shoot",5);
        JSONObject obj = JSONHelper.JSONForObject(p);
        JSONToObjectConverter<Post> convert = new JSONToObjectConverter<>(Post.class);
        Post recreated  = convert.createObjectFromJSON(Post.class, obj);
        System.out.println(recreated.toString());

    }


}

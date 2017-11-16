package database;

import com.google.firebase.database.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import util.exceptions.ObjectIdNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * The DatabaseConnector class offers an API for connecting to the online
 * realtime database for retrieval, storage, manipulation, and removal of
 * objects within the database.
 *
 * @author Walker Willetts
 *
 */
public class DatabaseConnector<T extends TrackableObject> extends FirebaseConnector {

    /* Instance Variables */
    private DatabaseReference dbRef;
    private Class<T> myClass;
    private ChildEventListener currentListener;
    private Gson JSONCreator;

    /**
     * Creates a new DatabaseConnector that is connected to the specified sector
     * of the Database online. The sector of the database corresponds to the class
     * name passed in to the constructor
     * @param className is a {@code Class<T>} that represents the class defined for
     *                  the database
     */
    public DatabaseConnector(Class<T> className) {
        myClass = className;
        dbRef = FirebaseDatabase.getInstance().getReference(myClass.getSimpleName());
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        JSONCreator = builder.create();
    }

    /**
     * Converts the {@code DataSnapshot} into its respective parameter
     * key -> value pairs
     * @param snap is a {@code DataSnapshot} representing the map of data
     *             returned from Firebase's realtime database
     * @return A {@code Map<String, Object>} that contains all the parsed
     * instanceVariable ->  value pairs from the snapshot
     */
    private Map<String, Object> parseParameters(DataSnapshot snap) {
        Map<String, Object> params = new HashMap<>();
        for(DataSnapshot child : snap.getChildren()) {
            params.put(child.getKey(), child.getValue());
            if(child.getValue() instanceof Long) {
                Number convertedVal = convertValue(child.getKey(), (Long) child.getValue());
                params.put(child.getKey(), convertedVal);
            }
        }
        return params;
    }

    /**
     * Converts the passed in value into the proper type for the
     * field type of the param
     * @param param is a {@code String} that is the string name of
     *              the field whose type you want
     * @param value is a {@code Long} that is the value you need to
     *              convert
     * @return A {@code Number} that contains the proper type for
     * the instance variable field
     */
    private Number convertValue(String param, Long value) {
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

    /**
     *  Creates a new object of type T using the parameters stored
     *  inside of the provided Map
     * @param params is a {@code Map<String, Object>} that contains
     *               instanceVariable names as keys and their Object
     *               as values
     * @return A {@code T} object built from the given parameters
     */
    private T createObject(Map<String, Object> params) {
        try {
            // Get constructor and create new instance of object
            Constructor<T> constructor = myClass.getDeclaredConstructor();
            T newObject;
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

            // Set subclass instance variables
            for(String param : params.keySet()) {
                Field instanceVar = newObject.getClass().getDeclaredField(param);
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

    /**
     * Creates an event listener that uses the passed in reactor in order
     * to decide on how to respond to changes in the data
     * @param reactor is a {@code DataReactor<T>} that specifies how to
     *                respond to changes in the data
     */
    public void listenToChanges(DataReactor<T> reactor) {
        if(currentListener != null) removeListener();
        currentListener = dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> params = parseParameters(dataSnapshot);
                T newObject = createObject(params);
                reactor.reactToNewData(newObject);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> params = parseParameters(dataSnapshot);
                T changedObject = createObject(params);
                reactor.reactToDataChanged(changedObject);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Map<String, Object> params = parseParameters(dataSnapshot);
                T removedObject = createObject(params);
                reactor.reactToDataRemoved(removedObject);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> params = parseParameters(dataSnapshot);
                T movedObject = createObject(params);
                reactor.reactToDataMoved(movedObject);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    /**
     * Removes event listeners from the data so that changes are no longer
     * registered with the {@code DataReactor<T>} that was passed into listenToChanges()
     */
    public void removeListener() {
        dbRef.removeEventListener(currentListener);
        currentListener = null;
    }

    /**
     * Adds an object of type T into the database. Note: If the object
     * is already contained within the database, then it will be overwritten.
     * @param objectToAdd is the object you want to add to the Database
     * @throws ObjectIdNotFoundException if the T object passed to the method
     * does not contain an id variable marked with the @Expose annotation
     */
    public void addToDatabase(T objectToAdd) throws ObjectIdNotFoundException {
        JSONObject tempJSONObj = new JSONObject(JSONCreator.toJson(objectToAdd));
        try {
            String uid = tempJSONObj.get("UID").toString();
            for(String key : tempJSONObj.keySet())
                dbRef.child(uid).child(key).setValueAsync(tempJSONObj.get(key));
        } catch (JSONException e) { throw new ObjectIdNotFoundException(); }
    }

    /**
     * Removes the passed in object from the database.
     * @param objectToRemove is the object you want to remove from the database
     * @throws ObjectIdNotFoundException if the T object passed to the method
     * does not contain an id variable marked with the @Expose annotation
     */
    public void removeFromDatabase(T objectToRemove) throws ObjectIdNotFoundException {
        JSONObject tempJSONObj = new JSONObject(JSONCreator.toJson(objectToRemove));
        try {
            String uid = tempJSONObj.get("UID").toString();
            dbRef.child(uid).removeValueAsync();
        } catch (JSONException e) { throw new ObjectIdNotFoundException(); }
    }
}

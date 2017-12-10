package database.firebase;

import com.google.firebase.database.*;
import database.jsonhelpers.JSONHelper;
import database.jsonhelpers.JSONToObjectConverter;
import org.json.JSONException;
import org.json.JSONObject;
import util.exceptions.ObjectIdNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
    private JSONToObjectConverter<T> converter;
    private ChildEventListener currentListener;

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
        converter = new JSONToObjectConverter<>(className);
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
                // Convert long to appropriate type for the object T
                Number convertedVal = convertValue(child.getKey(), (Long) child.getValue());
                params.put(child.getKey(), convertedVal);
            } else if(child.getValue().getClass() == DataSnapshot.class) {
                // Create map for params of object that is being held by the overall object
                params.put(child.getKey(), parseParameters((DataSnapshot)child.getValue()));
            }
        }
        return params;
    }

    /**
     * Converts the passed in value into the proper type for the
     * field type of the param
     * @param param is a {@code String} that is the string name of
     *              the field whose type you want
     * @param value is a {@code Long} that is the value you need to convert
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
                T newObject = converter.createObjectFromJSON(myClass, new JSONObject(params));
                reactor.reactToNewData(newObject);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> params = parseParameters(dataSnapshot);
                T changedObject = converter.createObjectFromJSON(myClass, new JSONObject(params));
                reactor.reactToDataChanged(changedObject);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Map<String, Object> params = parseParameters(dataSnapshot);
                T removedObject = converter.createObjectFromJSON(myClass, new JSONObject(params));
                reactor.reactToDataRemoved(removedObject);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> params = parseParameters(dataSnapshot);
                T movedObject = converter.createObjectFromJSON(myClass, new JSONObject(params));
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
        JSONObject tempJSON = JSONHelper.JSONForObject(objectToAdd);
        try {
            String uid = tempJSON.get("UID").toString();
            dbRef.child(uid).setValueAsync(JSONHelper.jsonMapFromObject(objectToAdd));
        } catch(JSONException e){ throw new ObjectIdNotFoundException(); }
    }

    /**
     * Removes the passed in object from the database.
     * @param objectToRemove is the object you want to remove from the database
     * @throws ObjectIdNotFoundException if the T object passed to the method
     * does not contain an id variable marked with the @Expose annotation
     */
    public void removeFromDatabase(T objectToRemove) throws ObjectIdNotFoundException {
        JSONObject tempJSON = JSONHelper.JSONForObject(objectToRemove);
        try {
            String uid = tempJSON.get("UID").toString();
            dbRef.child(uid).removeValueAsync();
        } catch (JSONException e) { throw new ObjectIdNotFoundException(); }
    }
}

package database;

import com.google.firebase.database.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import util.exceptions.ObjectIdNotFoundException;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The DatabaseConnector class offers an API for connecting to the online
 * database for retrieval, storage, manipulation, and removal of objects
 */
public class DatabaseConnector<T> extends FirebaseConnector {

    /* Instance Variables */
    private DatabaseReference dbRef;
    private Class<T> myClass;
    private ChildEventListener currentListener;
    private Gson JSONCreator;

    /**
     * Creates a new DatabaseConnector that is connected to the specified sector
     * of the Database online
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
     * Creates the object T for the given database connector from JSON stored
     * in the database
     * @param snap is a {@code DataSnapshot} representing the object to create
     * @return An object of type T that was initialized from the database, or
     * null if the object could not be created
     */
    private T createObjectFromData(DataSnapshot snap) {
        Class<?>[] paramTypes = new Class[(int) snap.getChildrenCount()];
        Object[] params = new Object[(int) snap.getChildrenCount()];
        int i = 0;
        for(DataSnapshot child : snap.getChildren()) {
            paramTypes[i] = child.getValue() instanceof Long ? Number.class : child.getValue().getClass();
            params[i] = child.getValue();
            i++;
        }
        Constructor<T> constructor = orderParamsProperly(paramTypes, params);
        return newObjectFromParams(constructor, params);
    }

    /**
     *  Orders the parameter objects so that they are in proper order for the first
     *  constructor of T that is found which can use the parameters
     * @param paramTypes is a {@code Class<?>[]} representing the parameter types
     *                   corresponding to the parameters read in by the database
     * @param params is a {@code Object[]} that contains the objects corresponding
     *               to the actual parameter values read in by the database
     * @return A {@code Constructor<T>} that represents the constructor to be used
     * for the properly ordered params
     */
    private Constructor<T> orderParamsProperly(Class<?>[] paramTypes, Object[] params) {
        List<Class<?>> currTypesRotation = Arrays.asList(paramTypes);
        List<Object> currParamsRotation = Arrays.asList(params);
        int rotationCount = 0;
        while(rotationCount < paramTypes.length) {
            Collections.rotate(currTypesRotation, 1);
            Collections.rotate(currParamsRotation, 1);
            try {
                Constructor<T> correctConstructor = myClass.getDeclaredConstructor(paramTypes);
                params = currParamsRotation.toArray();
                return correctConstructor;
            } catch(Exception e) {
                // Do nothing if a constructor doesn't exist for the parameter type
            }
            rotationCount++;
        }
        return null;
    }

    /**
     * Creates a new T object from the given constructor and the given parameters
     * @param constructor is a {@code Constructor<T>} that is the constructor to
     *                    use for creating the new object via reflection
     * @param params is a {@code Object[]} that contains all the parameter objects
     *               to use with the given constructor
     * @return Either a new {@code T} object that was instantiated using the provided
     * params, or null, indicating that the object was unable to be created via data
     * from the database
     */
    private T newObjectFromParams(Constructor<T> constructor, Object[] params) {
        try {
            if(constructor.isAccessible()) {
                T newObject = constructor.newInstance(params);
                return newObject;
            } else {
                constructor.setAccessible(true);
                T newObject = constructor.newInstance(params);
                constructor.setAccessible(false);
                return newObject;
            }
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
                T newObject = createObjectFromData(dataSnapshot);
                reactor.reactToNewData(newObject);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                T changedObject = createObjectFromData(dataSnapshot);
                reactor.reactToDataChanged(changedObject);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                T removedObject = createObjectFromData(dataSnapshot);
                reactor.reactToDataRemoved(removedObject);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                T movedObject = createObjectFromData(dataSnapshot);
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
     * registered with the DataReactor<T>
     */
    public void removeListener() {
        dbRef.removeEventListener(currentListener);
        currentListener = null;
    }

    /**
     * Adds an object of type T into the database. Note: If the object
     * is already contained within the database, then it will be overwritten
     * @param objectToAdd is the object you want to add to the Database
     * @throws ObjectIdNotFoundException if the T object passed to the method
     * does not contain an id variable marked with the @Expose annotation
     */
    public void addToDatabase(T objectToAdd) throws ObjectIdNotFoundException {
        JSONObject tempJSONObj = new JSONObject(JSONCreator.toJson(objectToAdd));
        try {
            String id = tempJSONObj.get("id").toString();
            for(String key : tempJSONObj.keySet()) {
                dbRef.child(id).child(key).setValueAsync(tempJSONObj.get(key));
            }
        } catch (JSONException e) {
            throw new ObjectIdNotFoundException();
        }
    }

    /**
     * Removes the passed in object from the database. Note: If the object
     * is not within the database, then
     * @param objectToRemove
     * @throws ObjectIdNotFoundException
     */
    public void removeFromDatabase(T objectToRemove) throws ObjectIdNotFoundException {
        JSONObject tempJSONObj = new JSONObject(JSONCreator.toJson(objectToRemove));
        try {
            String id = tempJSONObj.get("id").toString();
            dbRef.child(id).removeValueAsync();
        } catch (JSONException e) {
            throw new ObjectIdNotFoundException();
        }
    }

    /**
     * Removes the passed in the object with the passed in id from the
     * @param id is an {@code int} representing the id of the object to be removed
     */
    public void removeFromDatabase(int id) {
        dbRef.child("" + id).removeValueAsync();
    }
}

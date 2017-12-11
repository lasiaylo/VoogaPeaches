package database.firebase;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * An abstract class meant to be extended by any object that wishes to be
 * stored within the database. The class provides the setup for creating
 * unique identifiers for an object stored within the database.
 *
 * @author Walker Willetts
 */
public abstract class TrackableObject {

    /* Static Variables */
    private static final Map<String, TrackableObject> trackableObjects = new HashMap<>();

    /* Instance Variables*/
    @Expose protected String UID;

    /**
     * Constructor creates a new UID for the trackable object that allows
     * it to be stored within the database. Automatically creates a new
     * unique ID for the object.
     */
    public TrackableObject() {
        UID = UUID.randomUUID().toString().replace("-","");
        trackableObjects.put(UID, this);
    }

    /**
     * Initialize methods that performs further initialization once a trackable
     * object has been recreated from the database. In classes implementing this
     * method it can be safely assumed that @Expose marked variables will have their
     * values set before this method has been called.
     */
    abstract public void initialize();

    /**
     * Retrieves the tracked object when requested
     * @param UID is a {@code String} that represents the unique ID associated with the object you desire
     * @return The {@code TrackableObject} with the UID passed into the method
     */
    public static TrackableObject objectForUID(String UID) { return trackableObjects.getOrDefault(UID, null); }

    public String UIDforObject() {
        for (String each: trackableObjects.keySet()) {
            if (trackableObjects.get(each).equals(this)) {
                return each;
            }
        }
        return null;
    }

    /**
     * Adds the passed object to the map of tracked objects
     * @param object is a {@code TrackableObject} that is to be added to the map of tracked objects
     * @return {@code true} if the object is not already inside of the map, {@code false} otherwise
     */
    public static boolean trackTrackableObject(TrackableObject object) {
        if(trackableObjects.containsKey(object.UID)) return false;
        trackableObjects.put(object.UID, object);
        return true;
    }
}

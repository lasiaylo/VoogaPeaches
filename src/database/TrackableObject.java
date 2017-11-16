package database;

import com.google.gson.annotations.Expose;

import java.util.UUID;

/**
 * An abstract class meant to be extended by any object that wishes to be
 * stored within the database. The class provides the setup for creating
 * unique identifiers for an object stored within the database.
 *
 * @author Walker Willetts
 */
public abstract class TrackableObject {

    /* Instance Variables*/
    @Expose protected String UID;

    /**
     * Constructor creates a new UID for the trackable object that allows
     * it to be stored within the database. Automatically creates a new
     * unique ID for the object.
     */
    public TrackableObject() { UID = UUID.randomUUID().toString().replace("-",""); }

}

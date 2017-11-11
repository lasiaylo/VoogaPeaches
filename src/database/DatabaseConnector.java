package database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;

/**
 * The DatabaseConnector class offers an API for connecting to the online
 * database for retrieval of information
 */
public class DatabaseConnector<T> {

    /* Final Variables */
    private static final String API_CREDENTIAL_DIR = "./data/voogasalad-5152b-firebase-adminsdk-jn865-8e96d753e3.json";
    private static final String DATABASE_URL = "https://voogasalad-5152b.firebaseio.com";
    private static boolean appInitialized = false;

    /* Instance Variables */
    private DatabaseReference dbRef;
    private Class<T> myClass;
    private ChildEventListener currentListener;

    /**
     * Initializes the FirebaseApp singleton class that is then
     * used by every single DatabaseConnector instance
     */
    private void initializeFirebaseApp() {
        try {
            // Retrieve API key information for firebase
            FileInputStream apiStream = new FileInputStream(API_CREDENTIAL_DIR);
            // Define options for the database and initialize firebase application
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(apiStream))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
            appInitialized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new DatabaseConnector that is connected to the specified sector
     * of the Database online
     * @param className is a {@code Class<T>} that represents the class defined for
     *                  the database
     */
    public DatabaseConnector(Class<T> className) {
        if (!appInitialized) initializeFirebaseApp();
        myClass = className;
        dbRef = FirebaseDatabase.getInstance().getReference(myClass.getSimpleName());
    }

    /**
     * Creates the object T for the given database connector from JSON stored
     * in the database
     * @param snap is a {@code DataSnapshot} representing the object to create
     * @return An object of type T that was initialized from the database
     */
    private T createObjectFromData(DataSnapshot snap) {
        int numParams = myClass.getConstructors()[0].getParameterCount();
        Object[] paramValues = new Object[numParams];
        int i = 0;
        for (DataSnapshot child : snap.getChildren()) {
            paramValues[i] = child.getValue();
            i++;
        }
        try {
            return (T) myClass.getConstructors()[0].newInstance(paramValues);
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
                System.out.println(databaseError.getMessage().toString());
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
}

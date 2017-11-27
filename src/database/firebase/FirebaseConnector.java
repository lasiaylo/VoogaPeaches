package database.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

/**
 * The abstract class for any classes that wish to communicate with a Firebase service.
 * Ensures that only one FirebaseApp is created for an instance of the application.
 *
 * @author Walker Willetts
 */
public abstract class FirebaseConnector {

    /* Final Variables */
    private static final String API_CREDENTIAL_DIR = "./data/program_config/database_credentials.json";
    private static final String DATABASE_URL = "https://voogasalad-5152b.firebaseio.com";
    private static final String STORAGE_URL = "voogasalad-5152b.appspot.com";

    /* Static Variables */
    private static boolean appInitialized = false;
    private static FirebaseApp app;
    /**
     * Initializes the FirebaseApp if it hasn't been already. Should only
     * ever be called by subclasses within the database package.
     */
    protected FirebaseConnector() {
        if(!appInitialized) initializeFirebaseApp();
    }

    /**
     * Initializes the FirebaseApp for a single instance of the application.
     */
    private void initializeFirebaseApp() {
        try {
            // Retrieve API key information for firebase
            FileInputStream apiStream = new FileInputStream(API_CREDENTIAL_DIR);
            // Define options for the database, and initialize FirebaseApp
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(apiStream))
                    .setDatabaseUrl(DATABASE_URL)
                    .setStorageBucket(STORAGE_URL)
                    .build();
            app = FirebaseApp.initializeApp(options);
            appInitialized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the current FirebaseApp that is running. Note: This should be called
     * whenever the actual application closes to prevent blocking the java application.
     */
    public static void closeFirebaseApp() {
        if(app != null) app.delete();
    }
}

package database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class FirebaseCommunicator {
    private static final String API_CREDENTIAL_DIR = "./data/voogasalad-5152b-firebase-adminsdk-jn865-8e96d753e3.json";
    private static final String DATABASE_URL = "https://voogasalad-5152b.firebaseio.com";
    private static final String STORAGE_URL = "voogasalad-5152b.appspot.com";
    private static boolean appInitialized = false;

    protected FirebaseCommunicator() {
        if(!appInitialized) initializeFirebaseApp();
    }

    private void initializeFirebaseApp() {
        try {
            // Retrieve API key information for firebase
            FileInputStream apiStream = new FileInputStream(API_CREDENTIAL_DIR);
            // Define options for the database and initialize firebase application
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(apiStream))
                    .setDatabaseUrl(DATABASE_URL)
                    .setStorageBucket(STORAGE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
            appInitialized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

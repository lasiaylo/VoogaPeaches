package database;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.*;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.util.HashMap;

/**
 * The class handles communication with the storage bucket on
 * Firebase. The bucket online is responsible for holding user
 * input images and files
 */
public class FileStorageConnector extends FirebaseConnector {

    /* Instance Variables */
    private Bucket storageBucket;
    private DatabaseReference storageDBRef;
    private HashMap<String, String> filemap;

    /**
     * Creates a FileStorageConnector that can be used to
     * talk to the file storage bucket on Firebase
     */
    public FileStorageConnector() {
        storageBucket = StorageClient.getInstance().bucket();
        storageDBRef = FirebaseDatabase.getInstance().getReference("storage");
        filemap = new HashMap<>();
        setupListening();
    }

    /**
     * Sets up listening with the realtime Firebase DB in order to properly keep
     * track of the location and list of files stored within the file storage
     * in Firebase
     */
    private void setupListening() {
        storageDBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                filemap.put(dataSnapshot.getKey(), (String) dataSnapshot.getValue());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                filemap.put(dataSnapshot.getKey(), (String) dataSnapshot.getValue());
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                filemap.remove(dataSnapshot.getKey());
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                filemap.put(dataSnapshot.getKey(), (String) dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    /**
     * Saves the passed in File online to the Firebase file storage
     * @param file is a {@code File} representing the file to be saved online
     * @throws IOException when the File cannot be read
     */
    public void saveFile(File file) throws IOException {
        try {
            String fileName = file.getName().split("\\.")[0];
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            storageBucket.create(file.getName(), fileBytes);
            storageDBRef.child(fileName).setValueAsync(file.getName());
            filemap.put(fileName, file.getName());
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Returns the image requested that corresponds to the given name
     * @param image is a {@code String} representing the name of the image as saved online
     * @return An {@code Image} object that is the image saved online
     */
    public Image retrieveImage(String image) {
        Blob imageBlob = storageBucket.get(filemap.get(image));
        byte[] imageBytes = imageBlob.getContent();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        return new Image(inputStream);
    }

    /**
     * @return A {@code String[]} that contains all of the names of files stored online
     */
    public String[] fileNames() {
        return filemap.keySet().toArray(new String[0]);
    }
}

package database;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;

public class FileStorageConnector extends FirebaseCommunicator {

    private Bucket storageBucket;

    public FileStorageConnector() {
        storageBucket = StorageClient.getInstance().bucket();
    }

    public void saveImage(File image) {
        try {
            byte[] imageBytes = Files.readAllBytes(image.toPath());
            storageBucket.create(image.getName(), imageBytes);
        } catch (IOException e) {

        }
    }
}

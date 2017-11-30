package database.filehelpers;

import jdk.internal.util.xml.impl.Input;
import util.PropertiesReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for managing the storage and retrieval of files
 * located within the subfolders of /data/filedata/
 *
 * @author Walker Willetts
 */
public class FileDataManager {

    /* Instance Variables */
    private String baseFolder;

    /**
     * Enum defining the different folders in the data folder of the
     * project where user saved files can be read and written from by the
     * FileDataManager class
     */
    public enum FileDataFolders {
        IMAGES ("images"),
        SOUNDS ("sounds");

        private final String filepath;
        FileDataFolders(String path) { this.filepath = PropertiesReader.path("db_files") + path + "/"; }

        /**
         * @return A {@code String} representing the path of the folder within the project
         */
        String path() { return filepath; }
    }

    /**
     * Creates a new FileDataManager that is able to manipulate
     * files within the given folder within the data folder
     * @param folder is a {@code DataFolders} enum value that
     *               specifies which of the subfolders in data
     *               where you want to manipulate user saved files
     */
    public FileDataManager(FileDataFolders folder) {
        baseFolder = folder.path();
    }

    /**
     * Creates an InputStream of bytes corresponding to the passed in file name
     * @param filename is a {@code String} that represents the name of the file
     *                 to return a stream for
     * @return A {@code InputStream} that can then be used to create an image, sound,
     * etc. If the file is not found, then the stream will just be one for an empty
     * byte array
     */
    public InputStream readFileData(String filename) {
        byte[] fileBytes;
        try {
            Path fileLocation = Paths.get(baseFolder + filename);
            if(fileLocation.toFile().isFile()) {
                fileBytes = Files.readAllBytes(fileLocation);
            } else {
                fileBytes = new byte[0];
            }
        } catch(InvalidPathException | IOException e) {
            fileBytes = new byte[0];
        }
        return new ByteArrayInputStream(fileBytes);
    }

    /**
     * Retrieves all the files contained below the path specified. Includes files within subfolders
     * of the specified paths.
     * @param path is {@code String} specifying the subfolder to retrieve files from
     * @return A {@code List<InputStream>} that contains the InputStreams of each file in the subfolder
     */
    public List<InputStream> retrieveSubfolderFiles(String path) {
        List<InputStream> fileStreams = new ArrayList<>();
        File base = new File(baseFolder + path);
        if(base.exists()) {
            for(File subfile : base.listFiles()){
                if(subfile.isDirectory()) {
                    fileStreams.addAll(retrieveSubfolderFiles(path + "/" + subfile.getName()));
                } else {
                    InputStream fileStream = readFileData(path + "/" + subfile.getName());
                    fileStreams.add(fileStream);
                }
            }
        }
        return fileStreams;
    }

    /**
     * Deletes the specified User file
     * @param filename is a {@code String} representing the name of the file to
     *                 be deleted within the base folder
     * @return {@code true} if the file was successfully deleted, and {@code false}
     * otherwise
     */
    public boolean deleteFileData(String filename) {
        // Get the path to the file
        Path p = (new File(baseFolder + filename)).toPath();
        try {
            return Files.deleteIfExists(p);
        } catch(IOException e) {
            return false;
        }
    }

    /**
     * Writes the given array of bytes to the file specified.
     * @param fileBytes is a {@code byte[]} that represents the bytes of the file
     *                  to be written
     * @param filename is a {@code String} that represents the name of the file to
     *                 save the bytes as
     * @return {@code true} if the file successfully saves, and {@code false} otherwise
     */
    public boolean writeFileData(byte[] fileBytes, String filename) {
        try {
            Files.write(Paths.get(baseFolder + filename), fileBytes);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Returns whether or not the folder exists within the base folder's
     * directory
     * @param folderName is a {@code String} representing the name of the folder
     *                   to search for
     * @return {@code true} if the folder does exist, and {@code false} otherwise
     */
    public boolean folderExists(String folderName) {
        Path folderPath = Paths.get(baseFolder + folderName);
        return folderPath.toFile().exists();
    }

    /**
     * Creates a new folder within the base folder of the manager. If
     * the folder exists already, then a new folder will NOT be created.
     * @param folderName is a {@code String} representing the name
     *                   of the folder to create
     * @return {@code true} if the folder was created, and false otherwise
     */
    public boolean createFolder(String folderName){
        if(folderExists(folderName)) return false;
        return (new File(baseFolder + folderName)).mkdir();
    }
}

package database.filehelpers;

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
     * Deletes the specified User file
     * @param filename is a {@code String} representing the name of the file to
     *                 be deleted within the base folder
     * @return {@code true} if the file was successfully deleted, and {@code false}
     * otherwise
     */
    public boolean deleteFileData(String filename) {
        Path p = (new File(baseFolder + filename)).toPath();
        try { return Files.deleteIfExists(p); } catch (IOException e) { return false; }
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
        } catch (IOException e) { return false; }
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
     * Gets all subfolder directory names
     * @return A {@List<String>} of the subfolders within the base folder
     */
    public List<String> getSubFolder() {
        File[] directories = new File(baseFolder).listFiles(File::isDirectory);
        List<String> subFolder = new ArrayList<>();
        for (File each: directories)
            subFolder.add(each.getName());
        return subFolder;
    }

    /**
     * Returns of all subfiles within a given path
     * @param path is a {@code String} representing the base path to search through
     * @return A {@code List<String>} of all the files
     */
    public List<String> getSubFile(String path) {
        List<String> file = new ArrayList<>();
        File base = new File(baseFolder + path);
        for(File subfile : base.listFiles()){
            file.add(subfile.getName());
        }
        return file;
    }

}
